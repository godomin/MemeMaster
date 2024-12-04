package com.ykim.mememaster.presentation.create

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.model.MemeTextData
import com.ykim.mememaster.presentation.util.MemeFontType

data class CreateState(
    val templateResId: Int = 0,
    val editMode: EditMode = EditMode.ADD,
    val selectedFont: MemeFontType = MemeFontType.STROKE,
    val selectedFontSize: Float = 40.sp.value,
    val selectedColor: Color = Color.White,
    val selectedTextId: Long = -1,
    val textList: List<MemeTextData> = emptyList(),
)
