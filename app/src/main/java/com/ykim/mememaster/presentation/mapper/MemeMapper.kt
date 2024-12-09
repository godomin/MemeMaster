package com.ykim.mememaster.presentation.mapper

import android.net.Uri
import com.ykim.mememaster.domain.model.MemeData
import com.ykim.mememaster.presentation.model.Meme

fun MemeData.toMeme(): Meme {
    return Meme(
        imageUri = Uri.parse(imageUri),
        isFavorite = isFavorite,
        isSelected = isSelected,
        timestamp = timestamp
    )
}

fun Meme.toMemeData(fileName: String): MemeData {
    return MemeData(
        imageUri = fileName,
        isFavorite = isFavorite,
        isSelected = isSelected,
        timestamp = timestamp
    )
}