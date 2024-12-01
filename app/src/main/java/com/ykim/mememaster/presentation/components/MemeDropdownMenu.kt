package com.ykim.mememaster.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykim.mememaster.presentation.home.DropdownList
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun MemeDropdownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    selectedItem: DropdownList,
    onItemClick: (DropdownList) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .height(36.dp)
                .clickable { if (enabled) expanded = true },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = selectedItem.strId),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.secondary.copy(
                        alpha = if (enabled) 1f else 0.3f
                    ),
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary.copy(
                    alpha = if (enabled) 1f else 0.3f
                ),
                modifier = Modifier.size(24.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            Modifier.background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            DropdownList.entries.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(id = item.strId))
                    },
                    onClick = {
                        onItemClick(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MemeDropdownMenuPreview() {
    MemeMasterTheme {
        MemeDropdownMenu(
            enabled = true,
            selectedItem = DropdownList.FAVORITE
        )
    }
}