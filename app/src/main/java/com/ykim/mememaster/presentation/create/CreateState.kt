package com.ykim.mememaster.presentation.create

import com.ykim.mememaster.presentation.model.EditMode
import com.ykim.mememaster.presentation.model.OverlayText
import com.ykim.mememaster.presentation.model.TextStyle

data class CreateState(
    val templateResId: Int = 0,
    val editMode: EditMode = EditMode.ADD,
    val selectedText: OverlayText = OverlayText(),
    val textList: List<OverlayText> = emptyList(),
)
