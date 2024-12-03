package com.ykim.mememaster.presentation.navigation

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class Create(
    @DrawableRes val templateResId: Int,
)