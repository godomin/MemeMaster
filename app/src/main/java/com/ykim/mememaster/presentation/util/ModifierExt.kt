package com.ykim.mememaster.presentation.util

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.draggable(onDrag: (Offset) -> Unit): Modifier {
    return pointerInput(Unit) {
        detectDragGestures { _, dragAmount ->
            onDrag(dragAmount)
        }
    }
}

inline fun Modifier.applyIf(
    conditional: Boolean,
    whenTrue: Modifier.() -> Modifier,
    whenFalse: Modifier.() -> Modifier = { this }
): Modifier = if (conditional) then(whenTrue(Modifier)) else then(whenFalse(Modifier))