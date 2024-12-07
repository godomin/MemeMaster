package com.ykim.mememaster.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.create.MemeFont
import com.ykim.mememaster.presentation.create.fontList
import com.ykim.mememaster.presentation.util.MemeFontType

data class TextStyle(
    val font: MemeFontType = MemeFontType.STROKE,
    val size: Float = 40.sp.value,
    val color: Color = Color.White
) {
    fun getTextStyle(): TextStyle {
        return getMemeFont(font).style.copy(
            fontSize = size.sp,
            color = color,
            letterSpacing = 0.em,
        )
    }

    private fun getMemeFont(type: MemeFontType): MemeFont {
        return fontList.first { it.type == type }
    }
}
