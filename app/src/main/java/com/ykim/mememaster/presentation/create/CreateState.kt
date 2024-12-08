package com.ykim.mememaster.presentation.create

import com.ykim.mememaster.presentation.model.EditMode
import com.ykim.mememaster.presentation.model.OverlayText

data class CreateState(
    val templateResId: Int = 0,
    val editMode: EditMode = EditMode.ADD,
    val selectedText: OverlayText = OverlayText(),
    val textList: List<OverlayText> = emptyList(),
    val canUndo: Boolean = false,
    val canRedo: Boolean = false,
)
