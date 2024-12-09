package com.ykim.mememaster.domain

import com.ykim.mememaster.domain.model.ImageData
import java.io.File

interface ImageRepository {
    suspend fun saveImage(imageData: ImageData): String
    suspend fun saveImageInternalStorage(imageData: ImageData): String
}