package com.ykim.mememaster.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemeEntity(
    val fileName: String,
    val isFavorite: Boolean,
    val isSelected: Boolean,
    @PrimaryKey val timestamp: Long,
)
