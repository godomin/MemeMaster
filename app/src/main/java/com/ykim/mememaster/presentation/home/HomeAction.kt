package com.ykim.mememaster.presentation.home

sealed class HomeAction {
    data object OnCreateClicked : HomeAction()
    data class OnFilterChanged(val filter: DropdownList) : HomeAction()
}