package com.ykim.mememaster.presentation.model

data class Meme(
    val uri: String,
    val isFavorite: Boolean,
    val isSelected: Boolean,
    val timestamp: Long,
)
