package com.ykim.mememaster.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.presentation.create.MemeFont
import com.ykim.mememaster.presentation.create.fontList
import com.ykim.mememaster.presentation.util.MemeText

data class MemeTextEditorData(
    val font: MemeFont = fontList.first { it.data == MemeText.STROKE },
    val fontSize: Float = 40.sp.value,
    val color: Color = Color.White
)
