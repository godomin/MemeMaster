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
import java.io.File
import java.io.FileOutputStream
import java.util.Date

sealed class CapturingProgress {
    data object Idle : CapturingProgress()
    data object Capturing : CapturingProgress()
    data class Captured(val picture: Picture) : CapturingProgress()
    data class Error(val exception: Throwable) : CapturingProgress()
}

interface CaptureState {

    var progress: CapturingProgress

    fun capture()
}

class CaptureStateImpl : CaptureState {

    override var progress: CapturingProgress by mutableStateOf(CapturingProgress.Idle)

    override fun capture() {
        progress = CapturingProgress.Capturing
    }
}

@Composable
fun rememberCaptureState(): CaptureState {
    return remember { CaptureStateImpl() }
}

fun Modifier.capture(state: CaptureState): Modifier {
    return this.applyIf(state.progress == CapturingProgress.Capturing, {
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
                    state.progress = CapturingProgress.Captured(picture)
                }
            }
        } catch (e: Exception) {
            state.progress = CapturingProgress.Error(e)
            this
        }
    })
}

fun Picture.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawPicture(this)
    return bitmap
}

fun Bitmap.saveAsFile(filePath: String, fileName: String = Date().time.toString()): File? {
    return kotlin.runCatching {
        File(filePath, "${fileName}.jpg").apply {
            FileOutputStream(this).use {
                compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        }
    }.getOrNull()
}