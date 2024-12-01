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
    var state by mutableStateOf(
        HomeState(
            list = listOf(
                Meme(
                    uri = "",
                    isFavorite = true,
                    isSelected = true,
                    timestamp = 0L
                )
            )
        )
    )
        private set

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnCreateClicked -> {}
            is HomeAction.OnFilterChanged -> {
                state = state.copy(filter = action.filter)
            }
        }
    }
}