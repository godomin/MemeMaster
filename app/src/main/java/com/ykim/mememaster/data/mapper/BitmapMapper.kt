package com.ykim.mememaster.data.mapper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

suspend fun Bitmap.toByteArray(): ByteArray {
    return withContext(Dispatchers.Default) {
        ByteArrayOutputStream().use { outputStream ->
            compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.toByteArray()
        }
    }
}

suspend fun ByteArray.toBitmap(): Bitmap {
    return withContext(Dispatchers.Default) {
        BitmapFactory.decodeByteArray(this@toBitmap, 0, this@toBitmap.size)
    }
}