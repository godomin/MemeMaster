package com.ykim.mememaster.presentation.util

import com.ykim.mememaster.presentation.home.DropdownList
import com.ykim.mememaster.domain.model.MemeData

fun List<MemeData>.sortByFilter(filter: DropdownList): List<MemeData> {
    return sortedByDescending {
        when (filter) {
            DropdownList.FAVORITE -> if (it.isFavorite) 1 else 0
            DropdownList.NEW -> it.timestamp
        }
    }
}