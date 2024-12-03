package com.ykim.mememaster.presentation.create

import com.ykim.mememaster.presentation.util.MemeText

sealed interface CreateAction {
    data class OnTextFontChanged(val font: MemeText) : CreateAction
    data class OnTextFontSizeChanged(val fontSize: Float) : CreateAction

}