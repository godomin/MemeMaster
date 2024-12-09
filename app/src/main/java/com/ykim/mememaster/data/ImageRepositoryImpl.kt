package com.ykim.mememaster.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.ykim.mememaster.R
import com.ykim.mememaster.data.mapper.toBitmap
import com.ykim.mememaster.domain.ImageRepository
import com.ykim.mememaster.domain.model.ImageData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageRepository {

    override suspend fun saveImage(imageData: ImageData): String {
        val bitmap = imageData.byteArray.toBitmap()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageAbove29(imageData.fileName, bitmap)
        } else {
            saveImageBelow28(imageData.fileName, bitmap)
        }
        return saveImageToInternalStorage(imageData.fileName, bitmap)
    }

    override suspend fun saveImageInternalStorage(imageData: ImageData): String {
        val bitmap = imageData.byteArray.toBitmap()
        return saveImageToInternalStorage(imageData.fileName, bitmap)
    }

    private suspend fun saveImageToInternalStorage(fileName: String, bitmap: Bitmap): String {
        return try {
            val file = File(context.filesDir, fileName)
            withContext(Dispatchers.IO) {
                context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private suspend fun saveImageAbove29(fileName: String, bitmap: Bitmap) {
        val resolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/${context.getString(R.string.app_name)}"
            )
        }
        withContext(Dispatchers.IO) {
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                }
            }
        }
    }

    private suspend fun saveImageBelow28(fileName: String, bitmap: Bitmap) {
        val picturesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(picturesDir, fileName)
        withContext(Dispatchers.IO) {
            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
        }
        MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
    }
}