package com.ykim.mememaster.presentation.create

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.util.MemeFontType

data class MemeFont(
    val type: MemeFontType,
    val style: TextStyle
)

private val defaultTextStyle = TextStyle(
    color = Color.White,
    fontSize = 28.sp,
    letterSpacing = (-0.03).em
)

val fontList = listOf(
    MemeFont(
        MemeFontType.IMPACT,
        defaultTextStyle.copy(
            fontFamily = FontFamily(Font(R.font.impact)),
        )
    ),
    MemeFont(
        MemeFontType.STROKE,
        defaultTextStyle.copy(
            fontFamily = FontFamily(Font(R.font.impact)),
            fontWeight = FontWeight.ExtraBold,
        )
    ),
    MemeFont(
        MemeFontType.SHADOWED,
        defaultTextStyle.copy(
            fontFamily = FontFamily(Font(R.font.impact)),
            shadow = Shadow(
                color = Color.Black, offset = Offset(2f, 2f), blurRadius = 20f
            ),

        )
    ),
    MemeFont(
        MemeFontType.ROBOTO,
        defaultTextStyle.copy(
            fontFamily = FontFamily.SansSerif,
        )
    ),
    MemeFont(
        MemeFontType.ROCKSTAR,
        defaultTextStyle.copy(
            fontFamily = FontFamily(Font(R.font.rockstar_extra_bold)),
        )
    ),
    MemeFont(
        MemeFontType.RAZOR_FACE,
        defaultTextStyle.copy(
            fontFamily = FontFamily(Font(R.font.razor_face_regular)),
        )
    ),
)