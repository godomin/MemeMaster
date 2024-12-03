package com.ykim.mememaster.presentation.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ykim.mememaster.presentation.navigation.Create
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(CreateState())
        private set

    init {
        val templateResId = savedStateHandle.toRoute<Create>().templateResId
        state = state.copy(templateResId = templateResId)
    }

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