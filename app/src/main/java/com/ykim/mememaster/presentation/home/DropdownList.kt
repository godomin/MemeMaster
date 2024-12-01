package com.ykim.mememaster.presentation.home

import androidx.annotation.StringRes
import com.ykim.mememaster.R

enum class DropdownList(@StringRes val strId: Int) {
    FAVORITE(R.string.favorites_first),
    NEW(R.string.newest_first),
}