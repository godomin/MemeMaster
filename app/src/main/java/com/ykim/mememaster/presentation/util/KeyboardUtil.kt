package com.ykim.mememaster.presentation.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

@Composable
fun rememberKeyboardVisibility(): State<Boolean> {
    val bottom = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = bottom) {
        isKeyboardVisible.value = bottom > 200
    }
    return isKeyboardVisible
}