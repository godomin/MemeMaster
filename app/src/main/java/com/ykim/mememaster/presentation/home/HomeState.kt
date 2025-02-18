package com.ykim.mememaster.presentation.home

import com.ykim.mememaster.presentation.model.Meme

data class HomeState(
    val list : List<Meme> = emptyList(),
    val filter: DropdownList = DropdownList.FAVORITE,
    val mode: ItemMode = ItemMode.FAVORITE,
    val searchQuery: String = "",
    val isEditMode: Boolean = false,
    val resultList : List<Int> = emptyList(),
)