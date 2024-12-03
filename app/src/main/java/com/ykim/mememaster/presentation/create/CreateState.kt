package com.ykim.mememaster.presentation.create

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.util.MemeText

data class CreateState(
    val editMode: EditMode = EditMode.SIZE,
    val selectedFont: MemeText = MemeText.STROKE,
    val selectedFontSize: Float = 40.sp.value,
    val selectedColor: Color = Color.White
)
