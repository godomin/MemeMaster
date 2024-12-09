package com.ykim.mememaster.presentation.create

import android.graphics.Picture
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import com.ykim.mememaster.presentation.model.EditMode
import com.ykim.mememaster.presentation.model.OverlayText
import com.ykim.mememaster.presentation.util.MemeFontType

sealed interface CreateAction {
    data object OnNavigateUp : CreateAction

    data object OnAddText : CreateAction
    data object OnRemoveText : CreateAction
    data class OnTextChanged(val text: String) : CreateAction
    data class OnTextOffsetChanged(
        val imageSize: IntSize,
        val textSize: IntSize,
        val offset: Offset
    ) : CreateAction

    data class OnSelectedTextChanged(val text: OverlayText) : CreateAction

    data class OnEditModeChanged(val editMode: EditMode) : CreateAction

    data class OnTextFontChanged(val font: MemeFontType) : CreateAction
    data class OnTextFontSizeChanged(val size: Float) : CreateAction
    data class OnTextColorChanged(val color: Color) : CreateAction

    data object OnTextChangeApplied : CreateAction
    data object OnTextChangeDiscarded : CreateAction

    data object OnUndo : CreateAction
    data object OnRedo : CreateAction

    data class SaveMeme(val picture: Picture, val imageSize: IntSize) : CreateAction
    data class ShareMeme(val picture: Picture, val imageSize: IntSize) : CreateAction
}