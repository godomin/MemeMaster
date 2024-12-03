package com.ykim.mememaster.presentation.create

import androidx.compose.ui.graphics.Color
import com.ykim.mememaster.presentation.util.MemeText

sealed interface CreateAction {
    data object OnNavigateUp : CreateAction
    data class OnEditModeChanged(val editMode: EditMode) : CreateAction
    data class OnTextFontChanged(val font: MemeText) : CreateAction
    data class OnTextFontSizeChanged(val fontSize: Float) : CreateAction
    data class OnTextColorChanged(val color: Color) : CreateAction
}