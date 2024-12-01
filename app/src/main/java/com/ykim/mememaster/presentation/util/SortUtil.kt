package com.ykim.mememaster.presentation.util

import com.ykim.mememaster.presentation.home.DropdownList
import com.ykim.mememaster.presentation.model.Meme

fun List<Meme>.sortByFilter(filter: DropdownList): List<Meme> {
    return sortedByDescending {
        when (filter) {
            DropdownList.FAVORITE -> if (it.isFavorite) 1 else 0
            DropdownList.NEW -> it.timestamp
        }
    }
}