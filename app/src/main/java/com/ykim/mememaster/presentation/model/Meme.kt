package com.ykim.mememaster.presentation.model

import android.net.Uri

data class Meme(
    val imageUri: Uri,
    val isFavorite: Boolean,
    val isSelected: Boolean,
    val timestamp: Long,
)
