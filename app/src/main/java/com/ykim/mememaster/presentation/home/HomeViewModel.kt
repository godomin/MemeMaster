package com.ykim.mememaster.presentation.home

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private val eventChannel = Channel<HomeEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        // TODO: load from repo
        val list = mutableListOf<Meme>()
        (0..9).forEach {
            list += Meme(
                uri = "$it",
                isFavorite = false,
                isSelected = false,
                timestamp = 0L
            )
        }
        state = state.copy(
            list = list.sortByFilter(state.filter),
            resultList = getSearchResult(state.searchQuery)
        )
    }

    fun onAction(action: HomeAction) {
        when (action) {
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

    private fun onFilterChanged(filter: DropdownList) {
        if (state.filter != filter) {
            state = state.copy(
                list = state.list.sortByFilter(state.filter),
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
                if (it.uri == item.uri) {
                    newItem
                } else {
                    it
                }
            }
        )
        // TODO: update to repo
    }

    private fun onItemSelected(item: Meme, forceSelect: Boolean = false) {
        val newItem = item.copy(isSelected = !item.isSelected || forceSelect)
        state = state.copy(
            list = state.list.map {
                if (it.uri == item.uri) {
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
        state = state.copy(
            list = state.list.filter { !it.isSelected }
        )
        // TODO: update to repo
    }

    private fun shareSelectedItems() {
        val uriList = state.list.filter { it.isSelected }.map { Uri.parse(it.uri) }
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