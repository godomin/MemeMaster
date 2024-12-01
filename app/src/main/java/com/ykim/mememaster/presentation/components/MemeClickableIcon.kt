package com.ykim.mememaster.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun MemeClickableIcon(
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    selected: Boolean,
    unselectedAlphaEnabled: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = if (selected) selectedIcon else unselectedIcon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primaryContainer.copy(
                alpha = if (unselectedAlphaEnabled && !selected) 0.5f else 1.0f
            )
        )
    }
}

@Preview
@Composable
private fun MemeClickableIconPreview() {
    MemeMasterTheme {
        MemeClickableIcon(
            selectedIcon = Icons.Default.Favorite,
            unselectedIcon = Icons.Default.FavoriteBorder,
            selected = false,
            onClick = {}
        )
    }
}