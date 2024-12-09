package com.ykim.mememaster.data.mapper

import com.ykim.mememaster.data.database.MemeEntity
import com.ykim.mememaster.domain.model.MemeData

fun MemeData.toMemeEntity(): MemeEntity {
    return MemeEntity(
        fileName = imageUri,
        isFavorite = isFavorite,
        isSelected = isSelected,
        timestamp = timestamp
    )
}

fun MemeEntity.toMemeData(): MemeData {
    return MemeData(
        imageUri = fileName,
        isFavorite = isFavorite,
        isSelected = isSelected,
        timestamp = timestamp
    )
}