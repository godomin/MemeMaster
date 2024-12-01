package com.ykim.mememaster.presentation.home

import android.net.Uri

sealed interface HomeEvent {
    data class StartShareChooser(val uriList: ArrayList<Uri>) : HomeEvent
}