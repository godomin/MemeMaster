package com.ykim.mememaster.domain

import com.ykim.mememaster.domain.model.ImageData
import java.io.File

interface ImageRepository {
    suspend fun getImageFile(name: String): File?
    suspend fun deleteImages(images: List<String>)
    suspend fun saveImage(imageData: ImageData)
}