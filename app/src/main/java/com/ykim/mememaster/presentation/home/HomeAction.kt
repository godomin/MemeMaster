package com.ykim.mememaster.presentation.home

import com.ykim.mememaster.domain.model.MemeData

sealed interface HomeAction {
    data class OnFilterChanged(val filter: DropdownList) : HomeAction
    data class OnItemIconClicked(val item: MemeData) : HomeAction
    data class OnItemLongPressed(val item: MemeData) : HomeAction
    data object OnCancelSelect : HomeAction
    data object OnShareSelectedItem : HomeAction
    data object OnDeleteSelectedItem : HomeAction
    data class OnSearchQueryChanged(val query: String) : HomeAction
    data object OnEditModeChanged : HomeAction
    data class OnTemplateSelected(val templateResId: Int) : HomeAction
}