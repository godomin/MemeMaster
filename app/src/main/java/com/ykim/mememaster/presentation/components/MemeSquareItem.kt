package com.ykim.mememaster.presentation.components

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.home.ItemMode
import com.ykim.mememaster.presentation.model.Meme
import com.ykim.mememaster.presentation.util.getGradientBrush
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun MemeSquareItemExtended(
    item: Meme,
    mode: ItemMode,
    onIconClicked: () -> Unit = {},
    onLongPress: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    MemeSquareItem(
        model = item.imageUri,
        modifier = modifier,
        onClick = if (mode == ItemMode.SELECT) {
            onIconClicked
        } else {
            {}
        },
        onLongPress = onLongPress
    ) {
        val angle = if (mode == ItemMode.FAVORITE) -30f else 30f
        val colorStops = arrayOf(
            0.6f to MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0f),
            1.0f to MaterialTheme.colorScheme.surfaceDim.copy(alpha = 1f)
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = getGradientBrush(size, angle, colorStops)
            )
        }
        when (mode) {
            ItemMode.FAVORITE -> {
                MemeClickableIcon(
                    selectedIcon = Icons.Default.Favorite,
                    unselectedIcon = Icons.Default.FavoriteBorder,
                    selected = item.isFavorite,
                    onClick = onIconClicked,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

            ItemMode.SELECT -> {
                MemeClickableIcon(
                    selectedIcon = Icons.Default.CheckCircle,
                    unselectedIcon = ImageVector.vectorResource(id = R.drawable.icon_unchecked),
                    unselectedAlphaEnabled = true,
                    selected = item.isSelected,
                    onClick = onIconClicked,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MemeSquareItem(
    model: Any,
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(176.dp)
            .clip(RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(model),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        content()
    }
}

@Preview
@Composable
private fun MemeSquareItemPreview(modifier: Modifier = Modifier) {
    MemeMasterTheme {
        MemeSquareItemExtended(
            Meme(
                imageUri = Uri.parse(""),
                isFavorite = true,
                isSelected = false,
                timestamp = 0L
            ),
            mode = ItemMode.SELECT,
        )
    }
}