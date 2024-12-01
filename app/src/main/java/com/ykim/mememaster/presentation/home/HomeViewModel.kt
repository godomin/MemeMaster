package com.ykim.mememaster.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ykim.mememaster.presentation.model.Meme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        state = state.copy(
            list = listOf(
                Meme(
                    uri = "",
                    isFavorite = true,
                    isSelected = true,
                    timestamp = 0L
                )
            )
        )
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnCreateClicked -> {}
            is HomeAction.OnFilterChanged -> {
                state = state.copy(filter = action.filter)
            }

            HomeAction.OnCancelSelect -> {
                state = state.copy(mode = ItemMode.FAVORITE)
            }

            is HomeAction.OnItemIconClicked -> {
                when (state.mode) {
                    ItemMode.FAVORITE -> onFavoriteToggled(action.item)
                    ItemMode.SELECT -> onItemSelected(action.item)
                }
            }

            is HomeAction.OnItemLongPressed -> onItemLongPressed(action.item)
            HomeAction.OnDeleteSelectedItem -> TODO()
            HomeAction.OnShareSelectedItem -> TODO()
        }
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
        // update to repo
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
}