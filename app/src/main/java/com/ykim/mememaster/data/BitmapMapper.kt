package com.ykim.mememaster.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

object BitmapMapper {
    suspend fun bitmapToArray(bitmap: Bitmap): ByteArray {
        return withContext(Dispatchers.Default) {
            ByteArrayOutputStream().use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.toByteArray()
            }
        }
    }

    suspend fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
        return withContext(Dispatchers.Default) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}