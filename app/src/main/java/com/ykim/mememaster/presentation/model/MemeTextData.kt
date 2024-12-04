package com.ykim.mememaster.presentation.model

import androidx.compose.ui.geometry.Offset

data class MemeTextData(
    val offset: Offset,
    val id: Long = System.currentTimeMillis(),
    val text: String = "",
    val style: MemeTextStyleData = MemeTextStyleData(),
)