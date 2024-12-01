package com.ykim.mememaster.presentation.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun getGradientBrush(size: Size, angle: Float, colorStops: Array<Pair<Float, Color>>): Brush {
    val angleRad = angle / 180f * PI
    val x = cos(angleRad).toFloat()
    val y = sin(angleRad).toFloat()

    val radius = sqrt(size.width.pow(2) + size.height.pow(2)) / 2f
    val offset = size.center + Offset(x * radius, y * radius)

    val exactOffset = Offset(
        x = min(offset.x.coerceAtLeast(0f), size.width),
        y = size.height - min(offset.y.coerceAtLeast(0f), size.height)
    )
    return Brush.linearGradient(
        colorStops = colorStops,
        start = Offset(size.width, size.height) - exactOffset,
        end = exactOffset
    )
}