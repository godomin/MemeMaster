package com.ykim.mememaster.presentation.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.components.MemeDialog
import com.ykim.mememaster.presentation.components.MemeDropdownMenu
import com.ykim.mememaster.presentation.components.MemeFloatingActionButton
import com.ykim.mememaster.presentation.components.MemeIcon
import com.ykim.mememaster.presentation.components.MemeSearchField
import com.ykim.mememaster.presentation.components.MemeSquareItem
import com.ykim.mememaster.presentation.components.MemeSquareItemExtended
import com.ykim.mememaster.domain.model.MemeData
import com.ykim.mememaster.presentation.util.getGradientBrush
import com.ykim.mememaster.ui.theme.MemeMasterTheme

@Composable
fun HomeScreenRoot(
    onTemplateSelected: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
) {
    HomeScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnTemplateSelected -> {
                    onTemplateSelected(action.templateResId)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var showDeleteConfirmDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        floatingActionButton = {
            if (state.mode == ItemMode.FAVORITE) {
                MemeFloatingActionButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
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
                            MemeIcon(
                                icon = Icons.Default.Close,
                                onClick = { onAction(HomeAction.OnCancelSelect) }
                            )
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
                            MemeIcon(
                                icon = Icons.Default.Share,
                                onClick = { onAction(HomeAction.OnShareSelectedItem) }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            MemeIcon(
                                icon = Icons.Default.Delete,
                                onClick = { showDeleteConfirmDialog = true },
                                enabled = state.list.any { it.isSelected }
                            )
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
                                .padding(top = 22.dp, start = 22.dp, end = 22.dp)
                        ) {
                            items(
                                items = state.list,
                                key = { it.fileName }
                            ) { item ->
                                MemeSquareItemExtended(
                                    item = item,
                                    mode = state.mode,
                                    onIconClicked = { onAction(HomeAction.OnItemIconClicked(item)) },
                                    onLongPress = { onAction(HomeAction.OnItemLongPressed(item)) }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
                if (showBottomSheet) {
                    val sheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = false
                    )
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = sheetState,
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        dragHandle = {
                            BottomSheetDefaults.DragHandle(
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            MemeSearchField(
                                title = stringResource(id = R.string.choose_template_title),
                                value = state.searchQuery,
                                onValueChange = { onAction(HomeAction.OnSearchQueryChanged(it)) },
                                isEditMode = state.isEditMode,
                                onEditModeChanged = { onAction(HomeAction.OnEditModeChanged) }
                            )
                            if (state.isEditMode) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = pluralStringResource(
                                        R.plurals.result_template_count,
                                        state.resultList.size,
                                        state.resultList.size
                                    ),
                                    style = MaterialTheme.typography.bodySmall
                                        .copy(color = MaterialTheme.colorScheme.outline)
                                )
                            } else {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(id = R.string.choose_template_description),
                                    style = MaterialTheme.typography.bodySmall
                                        .copy(color = Color.White)
                                )
                            }
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 40.dp, start = 6.dp, end = 6.dp)
                            ) {
                                items(
                                    items = state.resultList,
                                    key = { it }
                                ) { item ->
                                    MemeSquareItem(
                                        model = item,
                                        onClick = {
                                            onAction(HomeAction.OnTemplateSelected(item))
                                            showBottomSheet = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            val colorStops = arrayOf(
                0.0f to MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0f),
                1.0f to MaterialTheme.colorScheme.surfaceDim.copy(alpha = 1f)
            )
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .align(Alignment.BottomCenter)
            ) {
                drawRect(
                    brush = getGradientBrush(
                        size = size,
                        angle = 270f,
                        colorStops = colorStops
                    )
                )
            }
            if (showDeleteConfirmDialog) {
                val selectedCount = state.list.count { it.isSelected }
                MemeDialog(
                    title = pluralStringResource(
                        R.plurals.delete_meme_title,
                        selectedCount,
                        selectedCount
                    ),
                    description = stringResource(id = R.string.delete_meme_description),
                    confirmText = stringResource(id = R.string.delete),
                    dismissText = stringResource(id = R.string.cancel),
                    onConfirm = {
                        onAction(HomeAction.OnDeleteSelectedItem)
                        showDeleteConfirmDialog = false
                    },
                    onDismiss = { showDeleteConfirmDialog = false }
                )
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
                    MemeData(
                        fileName = "1",
                        isFavorite = true,
                        isSelected = true,
                        timestamp = 0L
                    ),
                    MemeData(
                        fileName = "2",
                        isFavorite = false,
                        isSelected = false,
                        timestamp = 0L
                    ),
                    MemeData(
                        fileName = "3",
                        isFavorite = false,
                        isSelected = false,
                        timestamp = 0L
                    )
                ),
                filter = DropdownList.FAVORITE,
                mode = ItemMode.FAVORITE
            ),
            onAction = {}
        )
    }
}