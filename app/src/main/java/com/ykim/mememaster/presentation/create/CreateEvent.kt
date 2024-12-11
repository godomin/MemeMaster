package com.ykim.mememaster.presentation.create

import android.net.Uri

sealed interface CreateEvent {
    data class StartShareChooser(val uri: Uri) : CreateEvent
}