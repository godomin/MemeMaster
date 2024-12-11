package com.ykim.mememaster.presentation.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykim.mememaster.domain.MemeRepository
import com.ykim.mememaster.domain.model.MemeData
import com.ykim.mememaster.presentation.mapper.toMeme
import com.ykim.mememaster.presentation.mapper.toMemeData
import com.ykim.mememaster.presentation.model.Meme
import com.ykim.mememaster.presentation.util.sortByFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val memeRepository: MemeRepository,
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadData -> loadData()

            is HomeAction.OnFilterChanged -> onFilterChanged(action.filter)

            HomeAction.OnCancelSelect -> onCancelSelected()

            is HomeAction.OnItemIconClicked -> {
                when (state.mode) {
                    ItemMode.FAVORITE -> onFavoriteToggled(action.item)
                    ItemMode.SELECT -> onItemSelected(action.item)
                }
            }

            is HomeAction.OnItemLongPressed -> onItemLongPressed(action.item)
            HomeAction.OnDeleteSelectedItem -> deleteSelectedItems()
            HomeAction.OnShareSelectedItem -> shareSelectedItems()

            is HomeAction.OnSearchQueryChanged -> onQueryChanged(action.query)

            HomeAction.OnEditModeChanged -> {
                state = state.copy(isEditMode = !state.isEditMode)
            }

            else -> Unit
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            state = state.copy(
                list = memeRepository.getMemes().map { it.toMeme() }.sortByFilter(state.filter),
                resultList = getSearchResult(state.searchQuery)
            )
        }
    }

    private fun onFilterChanged(filter: DropdownList) {
        if (state.filter != filter) {
            state = state.copy(
                list = state.list.sortByFilter(filter),
                filter = filter
            )
        }
    }

    private fun onCancelSelected() {
        state = state.copy(
            mode = ItemMode.FAVORITE,
            list = state.list.map { it.copy(isSelected = false) }
        )
    }

    private fun onFavoriteToggled(item: Meme) {
        val newItem = item.copy(isFavorite = !item.isFavorite)
        state = state.copy(
            list = state.list.map {
                if (it.timestamp == item.timestamp) {
                    newItem
                } else {
                    it
                }
            }.sortByFilter(state.filter)
        )
        viewModelScope.launch {
            memeRepository.upsertMeme(
                MemeData(
                    imageUri = newItem.imageUri.toString(),
                    isFavorite = newItem.isFavorite,
                    isSelected = newItem.isSelected,
                    timestamp = newItem.timestamp
                )
            )
        }
    }

    private fun onItemSelected(item: Meme, forceSelect: Boolean = false) {
        val newItem = item.copy(isSelected = !item.isSelected || forceSelect)
        state = state.copy(
            list = state.list.map {
                if (it.timestamp == item.timestamp) {
                    newItem
                } else {
                    it
                }
            }
        )
    }

    private fun onItemLongPressed(item: Meme) {
        onItemSelected(item, true)
        state = state.copy(mode = ItemMode.SELECT)
    }

    private fun deleteSelectedItems() {
        val (deleted, left) = state.list.partition { it.isSelected }
        state = state.copy(
            list = left
        )
        viewModelScope.launch {
            memeRepository.deleteMemes(deleted.map { it.toMemeData() })
        }
    }

    private fun shareSelectedItems() {
        val uriList = state.list.filter { it.isSelected }.map { it.imageUri }
        viewModelScope.launch {
            eventChannel.send(HomeEvent.StartShareChooser(ArrayList(uriList)))
        }
    }

    private fun onQueryChanged(query: String) {
        state = state.copy(
            resultList = getSearchResult(query = query),
            searchQuery = query
        )
    }
}