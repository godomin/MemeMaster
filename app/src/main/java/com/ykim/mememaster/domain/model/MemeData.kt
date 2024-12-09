package com.ykim.mememaster.domain.model

data class MemeData(
    val imageUri: String,
    val isFavorite: Boolean,
    val isSelected: Boolean,
    val timestamp: Long,
)
