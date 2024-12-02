package com.ykim.mememaster.presentation.create

import com.ykim.mememaster.presentation.util.MemeText

data class CreateState(
    val editMode: EditMode = EditMode.ADD,
    val selectedFont: MemeText = MemeText.STROKE,
)
