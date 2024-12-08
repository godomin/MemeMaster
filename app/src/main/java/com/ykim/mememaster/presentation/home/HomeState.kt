package com.ykim.mememaster.presentation.home

import com.ykim.mememaster.domain.model.MemeData

data class HomeState(
    val list : List<MemeData> = emptyList(),
    val filter: DropdownList = DropdownList.FAVORITE,
    val mode: ItemMode = ItemMode.FAVORITE,
    val searchQuery: String = "",
    val isEditMode: Boolean = false,
    val resultList : List<Int> = emptyList(),
)