package com.ykim.mememaster.presentation.util

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntSize

sealed class CapturingProgress {
    data object Idle : CapturingProgress()
    data class Capturing(val isInternal: Boolean) : CapturingProgress()
    data class Captured(val picture: Picture, val isInternal: Boolean) : CapturingProgress()
    data class Error(val exception: Throwable) : CapturingProgress()
}

interface CaptureState {

    var progress: CapturingProgress

    fun capture()

    fun captureInternal()
}

class CaptureStateImpl : CaptureState {

    override var progress: CapturingProgress by mutableStateOf(CapturingProgress.Idle)

    override fun capture() {
        progress = CapturingProgress.Capturing(isInternal = false)
    }

    override fun captureInternal() {
        progress = CapturingProgress.Capturing(isInternal = true)
    }
}

@Composable
fun rememberCaptureState(): CaptureState {
    return remember { CaptureStateImpl() }
}

fun Modifier.capture(state: CaptureState): Modifier {
    return this.applyIf(state.progress is CapturingProgress.Capturing, {
        val isInternal =
            state.progress is CapturingProgress.Capturing && (state.progress as CapturingProgress.Capturing).isInternal
        try {
            drawWithCache {
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()

                onDrawWithContent {
                    val picture = Picture()

                    val pictureCanvas = Canvas(picture.beginRecording(width, height))
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()

                    drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                    state.progress = CapturingProgress.Captured(picture, isInternal)
                }
            }
        } catch (e: Exception) {
            state.progress = CapturingProgress.Error(e)
            this
        }
    })
}

fun Picture.toBitmap(imageSize: IntSize): Bitmap {
    val bitmap = Bitmap.createBitmap(
        imageSize.width,
        imageSize.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.clipRect(0f, 0f, imageSize.width.toFloat(), imageSize.height.toFloat())
    canvas.translate(-(width - imageSize.width) / 2f, -(height - imageSize.height) / 2f)
    canvas.drawPicture(this)
    return bitmap
}