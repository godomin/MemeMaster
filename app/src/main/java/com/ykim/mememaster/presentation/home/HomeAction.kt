package com.ykim.mememaster.presentation.home

import com.ykim.mememaster.presentation.model.Meme

sealed interface HomeAction {
    data class OnFilterChanged(val filter: DropdownList) : HomeAction
    data class OnItemIconClicked(val item: Meme) : HomeAction
    data class OnItemLongPressed(val item: Meme) : HomeAction
    data object OnCancelSelect : HomeAction
    data object OnShareSelectedItem : HomeAction
    data object OnDeleteSelectedItem : HomeAction
    data class OnSearchQueryChanged(val query: String) : HomeAction
    data object OnEditModeChanged : HomeAction
    data class OnTemplateSelected(val templateResId: Int) : HomeAction
}