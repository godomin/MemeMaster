package com.ykim.mememaster.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ykim.mememaster.R

private val manrope = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),        // 400
    Font(R.font.manrope_medium, FontWeight.Medium),         // 500
    Font(R.font.manrope_semi_bold, FontWeight.SemiBold),    // 600
    Font(R.font.manrope_bold, FontWeight.Bold),             // 700
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),

    headlineLarge = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)