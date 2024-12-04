package com.ykim.mememaster.presentation.util

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import com.ykim.mememaster.presentation.create.MemeFont

enum class MemeFontType {
    IMPACT,
    STROKE,
    SHADOWED,
    ROBOTO,
    RAZOR_FACE,
    ROCKSTAR,
    ;

    fun getDisplayedName(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")
    }
}

private const val displayText = "GOOD"

@Composable
fun MemeFontType.getTextComposable(fontData: MemeFont) {
    when (this) {
        MemeFontType.STROKE -> {
            Box {
                Text(text = displayText, style = fontData.style)
                Text(text = displayText, style = fontData.style.copy(
                    color = Color.Black,
                    drawStyle = Stroke(miter = 10f, width = 4f, join = StrokeJoin.Round)
                ))
            }
        }
        else -> {
            Text(text = displayText, style = fontData.style)
        }
    }
}