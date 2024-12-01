package com.ykim.mememaster.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykim.mememaster.R
import com.ykim.mememaster.ui.theme.MemeMasterTheme
import com.ykim.mememaster.ui.theme.PurpleMedium2

@Composable
fun MemeSearchField(
    title: String,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    isEditMode: Boolean,
    onEditModeChanged: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isEditMode) 68.dp else 52.dp)
    ) {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isEditMode) {
                MemeIcon(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = { onEditModeChanged() }
                )
                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .weight(1f),
                    textStyle = MaterialTheme.typography.labelMedium
                        .copy(color = Color.White),
                    cursorBrush = SolidColor(PurpleMedium2),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (value.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.search_hint),
                                    style = MaterialTheme.typography.labelMedium
                                        .copy(color = MaterialTheme.colorScheme.outline)
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                        .copy(color = Color.White.copy(alpha = 0.2f))
                )
                MemeIcon(
                    icon = Icons.Default.Search,
                    onClick = { onEditModeChanged() }
                )
            }
        }
        if (isEditMode) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.White.copy(alpha = 0.2f))
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview
@Composable
private fun MemeSearchFieldPreview() {
    MemeMasterTheme {
        MemeSearchField(
            value = "input",
            title = "Choose template",
            isEditMode = false,
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow),
        )
    }
}