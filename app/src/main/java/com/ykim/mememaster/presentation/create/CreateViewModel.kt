package com.ykim.mememaster.presentation.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(

) : ViewModel() {
    var state by mutableStateOf(CreateState())
        private set

    fun onAction(action: CreateAction) {
        when (action) {
            is CreateAction.OnEditModeChanged -> {
                state = state.copy(editMode = action.editMode)
            }

            is CreateAction.OnTextFontChanged -> {
                state = state.copy(selectedFont = action.font)
            }

            is CreateAction.OnTextFontSizeChanged -> {
                state = state.copy(selectedFontSize = action.fontSize)
            }

            is CreateAction.OnTextColorChanged -> {
                state = state.copy(selectedColor = action.color)
            }

            else -> Unit
        }
    }
}