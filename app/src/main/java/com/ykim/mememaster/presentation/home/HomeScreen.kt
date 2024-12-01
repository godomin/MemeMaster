package com.ykim.mememaster.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.components.MemeDropdownMenu
import com.ykim.mememaster.presentation.components.MemeFloatingActionButton
import com.ykim.mememaster.presentation.components.MemeSquareItemExtended
import com.ykim.mememaster.presentation.model.Meme
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun HomeScreenRoot(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            MemeFloatingActionButton(
                onClick = { onAction(HomeAction.OnCreateClicked) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (state.mode == ItemMode.FAVORITE) {
                    Text(
                        text = stringResource(id = R.string.home_title),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                    )
                    MemeDropdownMenu(
                        enabled = state.list.isNotEmpty(),
                        selectedItem = state.filter,
                        onItemClick = { onAction(HomeAction.OnFilterChanged(it)) }
                    )
                } else if (state.mode == ItemMode.SELECT) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { onAction(HomeAction.OnCancelSelect) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = state.list.filter { it.isSelected }.size.toString(),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { onAction(HomeAction.OnShareSelectedItem) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable { onAction(HomeAction.OnDeleteSelectedItem) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (state.list.isEmpty()) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.home_empty),
                        contentDescription = "",
                        modifier = Modifier.size(width = 141.dp, height = 200.dp)
                    )
                    Spacer(modifier = Modifier.height(34.dp))
                    Text(
                        text = stringResource(id = R.string.empty_home_description),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(22.dp)
                    ) {
                        items(
                            items = state.list,
                            key = { it.uri }
                        ) { item ->
                            MemeSquareItemExtended(
                                item = item,
                                mode = state.mode,
                                onIconClicked = { onAction(HomeAction.OnItemIconClicked(item)) },
                                onLongPress = { onAction(HomeAction.OnItemLongPressed(item)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MemeMasterTheme {
        HomeScreen(
            state = HomeState(
                list = listOf(
                    Meme(
                        uri = "1",
                        isFavorite = true,
                        isSelected = true,
                        timestamp = 0L
                    ),
                    Meme(
                        uri = "2",
                        isFavorite = false,
                        isSelected = false,
                        timestamp = 0L
                    ),
                    Meme(
                        uri = "3",
                        isFavorite = false,
                        isSelected = false,
                        timestamp = 0L
                    )
                ),
                filter = DropdownList.FAVORITE,
                mode = ItemMode.SELECT
            ),
            onAction = {}
        )
    }
}