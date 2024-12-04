package com.ykim.mememaster.presentation.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize


@Composable
fun pxToDp(px: Float): Dp {
    val density = LocalDensity.current
    return with(density) { px.toDp() }
}

fun Context.dpToPx(dp: Float): Float {
    val density = resources.displayMetrics.density
    return dp * density
}

fun getImageSize(imageSize: IntSize, bitmapWidth: Int, bitmapHeight: Int): IntSize {
    val imageRatio = imageSize.width.toFloat() / imageSize.height.toFloat()
    val bitmapRatio = bitmapWidth.toFloat() / bitmapHeight.toFloat()
    return if (imageRatio > bitmapRatio) {
        IntSize(
            width = (imageSize.height * bitmapRatio).toInt(),
            height = imageSize.height
        )
    } else {
        IntSize(
            width = imageSize.width,
            height = (imageSize.width / bitmapRatio).toInt()
        )
    }
}