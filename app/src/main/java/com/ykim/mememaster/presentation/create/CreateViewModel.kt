package com.ykim.mememaster.presentation.create

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.model.MemeTextData
import com.ykim.mememaster.presentation.navigation.Create
import com.ykim.mememaster.presentation.util.MemeFontType
import com.ykim.mememaster.presentation.util.dpToPx
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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

            is CreateAction.OnAddText -> {
                val newText = MemeTextData(
                    text = context.getString(R.string.editor_default_text)
                )
                state = state.copy(
                    editMode = EditMode.NONE,
                    textList = state.textList + newText,
                    selectedTextId = newText.id
                )
            }

            is CreateAction.OnTextChanged -> {
                state = state.copy(
                    textList = state.textList.map {
                        if (it.id == state.selectedTextId) {
                            it.copy(text = action.text)
                        } else {
                            it
                        }
                    }
                )
            }

            is CreateAction.OnTextOffsetChanged -> {
                state = state.copy(
                    textList = state.textList.map {
                        if (it.id == state.selectedTextId) {
                            it.copy(
                                offset = coercedOffset(
                                    action.imageSize,
                                    action.textSize,
                                    it.offset + action.offset
                                )
                            )
                        } else {
                            it
                        }
                    }
                )
            }

            is CreateAction.OnSelectedTextChanged -> {
                if (action.id == -1L) {
                    stateToAddMode()
                } else {
                    state = state.copy(
                        selectedTextId = action.id,
                        editMode = EditMode.NONE
                    )
                }
            }

            CreateAction.OnRemoveText -> {
                state = state.copy(
                    textList = state.textList.filter { it.id != state.selectedTextId },
                    selectedTextId = -1,
                    editMode = EditMode.ADD
                )
                resetSelectedStyle()
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

            CreateAction.OnTextChangeDiscarded -> {
                stateToAddMode()
            }

            CreateAction.OnTextChangeApplied -> {
                // TODO: apply text style
                stateToAddMode()
            }

            else -> Unit
        }
    }

    private fun stateToAddMode() {
        state = state.copy(
            selectedTextId = -1,
            editMode = EditMode.ADD,
            selectedFont = MemeFontType.STROKE,
            selectedFontSize = 40.sp.value,
            selectedColor = Color.White
        )
    }

    private fun resetSelectedStyle() {
        state = state.copy(
            selectedFont = MemeFontType.STROKE,
            selectedFontSize = 40.sp.value,
            selectedColor = Color.White
        )
    }

    private fun coercedOffset(imageSize: IntSize, textSize: IntSize, newOffset: Offset): Offset {
        val padding = context.dpToPx(5f)
        val left = (-imageSize.center.x + textSize.center.x - padding)
        val right = (imageSize.center.x - textSize.center.x + padding)
        val top = (-imageSize.center.y + textSize.center.y - padding)
        val bottom = (imageSize.center.y - textSize.center.y + padding)
        val coercedX = newOffset.x.coerceIn(left, right)
        val coercedY = newOffset.y.coerceIn(top, bottom)
        return Offset(coercedX, coercedY)
    }
}