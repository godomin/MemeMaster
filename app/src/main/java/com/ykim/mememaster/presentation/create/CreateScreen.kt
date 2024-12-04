package com.ykim.mememaster.presentation.create

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.components.MemeButton
import com.ykim.mememaster.presentation.components.MemeDialog
import com.ykim.mememaster.presentation.components.MemeIcon
import com.ykim.mememaster.presentation.components.MemeOutlinedButton
import com.ykim.mememaster.presentation.components.MemeSlider
import com.ykim.mememaster.presentation.util.getTextComposable
import com.ykim.mememaster.ui.theme.MemeMasterTheme
import com.ykim.mememaster.ui.theme.SurfaceContainerHighDark

@Composable
fun CreateScreenRoot(
    onNavigateUp: () -> Unit,
    viewModel: CreateViewModel = hiltViewModel(),
) {
    CreateScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                CreateAction.OnNavigateUp -> {
                    onNavigateUp()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CreateScreen(
    state: CreateState,
    onAction: (CreateAction) -> Unit
) {
    var showLeaveEditorDialog by remember { mutableStateOf(false) }
    BackHandler {
        showLeaveEditorDialog = true
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                MemeIcon(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    onClick = { showLeaveEditorDialog = true },
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = stringResource(id = R.string.new_meme),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(state.templateResId),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            ) {
                Box(modifier = Modifier.weight(1f))
                Divider()
                if (state.editMode == EditMode.ADD) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .padding(start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row {
                            MemeIcon(
                                icon = ImageVector.vectorResource(id = R.drawable.icon_undo),
                                onClick = { /*TODO*/ },
                                enabled = true
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            MemeIcon(
                                icon = ImageVector.vectorResource(id = R.drawable.icon_redo),
                                onClick = { /*TODO*/ },
                                enabled = true
                            )
                        }
                        MemeOutlinedButton(
                            text = stringResource(id = R.string.add_text),
                            onClick = {}
                        )
                        MemeButton(
                            text = stringResource(id = R.string.save_meme),
                            onClick = {}
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainerLow),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        when (state.editMode) {
                            EditMode.FONT -> {
                                val listState = rememberLazyListState()
                                LaunchedEffect(key1 = Unit) {
                                    val targetIndex =
                                        fontList.map { it.type }.indexOf(state.selectedFont)
                                    val middleOffset = listState.layoutInfo.viewportSize.width / 2
                                    listState.scrollToItem(
                                        index = targetIndex,
                                        scrollOffset = -middleOffset
                                    )
                                }
                                LazyRow(
                                    state = listState,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    items(
                                        items = fontList
                                    ) { memeFont ->
                                        Column(
                                            modifier = when {
                                                state.selectedFont == memeFont.type -> Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(SurfaceContainerHighDark)
                                                    .padding(8.dp)

                                                else -> Modifier
                                                    .clickable {
                                                        onAction(
                                                            CreateAction.OnTextFontChanged(
                                                                memeFont.type
                                                            )
                                                        )
                                                    }
                                                    .padding(8.dp)
                                            },
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            memeFont.type.getTextComposable(memeFont)
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = memeFont.type.getDisplayedName(),
                                                style = MaterialTheme.typography.bodySmall
                                                    .copy(
                                                        color = Color.White,
                                                        fontSize = 10.sp
                                                    )
                                            )
                                        }
                                    }
                                }
                            }

                            EditMode.SIZE -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier.size(36.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Aa",
                                            style = MaterialTheme.typography.headlineLarge.copy(
                                                fontSize = 12.sp,
                                                color = Color.White
                                            )
                                        )
                                    }
                                    MemeSlider(
                                        value = state.selectedFontSize,
                                        onValueChange = {
                                            onAction(
                                                CreateAction.OnTextFontSizeChanged(
                                                    it
                                                )
                                            )
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                    Box(
                                        modifier = Modifier.size(36.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Aa",
                                            style = MaterialTheme.typography.headlineLarge.copy(
                                                color = Color.White
                                            )
                                        )
                                    }
                                }
                            }

                            EditMode.COLOR -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp, start = 12.dp, end = 12.dp)
                                ) {
                                    val listState = rememberLazyListState()
                                    LaunchedEffect(key1 = Unit) {
                                        val targetIndex = colorList.indexOf(state.selectedColor)
                                        val middleOffset =
                                            listState.layoutInfo.viewportSize.width / 2
                                        listState.scrollToItem(
                                            index = targetIndex,
                                            scrollOffset = -middleOffset
                                        )
                                    }
                                    LazyRow(
                                        state = listState,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        items(
                                            items = colorList
                                        ) { color ->
                                            Box(
                                                modifier = when {
                                                    state.selectedColor == color -> Modifier
                                                        .size(44.dp)
                                                        .clip(CircleShape)
                                                        .background(Color.White.copy(alpha = 0.2f))

                                                    else -> Modifier
                                                        .size(44.dp)
                                                },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(32.dp)
                                                        .clip(CircleShape)
                                                        .background(color)
                                                        .clickable {
                                                            onAction(
                                                                CreateAction.OnTextColorChanged(
                                                                    color
                                                                )
                                                            )
                                                        }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            else -> {}
                        }
                    }
                    if (state.editMode == EditMode.NONE) {
                        Divider()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .padding(start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MemeIcon(
                            icon = Icons.Default.Close,
                            onClick = { onAction(CreateAction.OnEditModeChanged(EditMode.ADD)) }
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            ToolbarIcon(
                                selected = state.editMode == EditMode.FONT,
                                onClick = { onAction(CreateAction.OnEditModeChanged(EditMode.FONT)) }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_font_style),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            ToolbarIcon(
                                selected = state.editMode == EditMode.SIZE,
                                onClick = { onAction(CreateAction.OnEditModeChanged(EditMode.SIZE)) }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_font_size),
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            ToolbarIcon(
                                selected = state.editMode == EditMode.COLOR,
                                onClick = { onAction(CreateAction.OnEditModeChanged(EditMode.COLOR)) }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_font_color),
                                    contentDescription = "",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        MemeIcon(
                            icon = Icons.Default.Check,
                            onClick = { onAction(CreateAction.OnEditModeChanged(EditMode.ADD)) }
                        )
                    }
                }
            }
        }
        if (showLeaveEditorDialog) {
            MemeDialog(
                title = stringResource(id = R.string.leave_editor_title),
                description = stringResource(id = R.string.leave_editor_description),
                confirmText = stringResource(id = R.string.leave),
                dismissText = stringResource(id = R.string.cancel),
                onConfirm = {
                    onAction(CreateAction.OnNavigateUp)
                    showLeaveEditorDialog = false
                },
                onDismiss = { showLeaveEditorDialog = false }
            )
        }
    }
}

@Composable
private fun Divider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                color = Color(0xFF27252B)
            )
    )
}

@Composable
private fun ToolbarIcon(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = when {
            selected -> {
                modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerHighDark)
                    .clickable(onClick = onClick)
            }

            else -> {
                modifier
                    .size(48.dp)
                    .clickable(onClick = onClick)
            }
        },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
private fun CreateScreenScreenPreview() {
    MemeMasterTheme {
        CreateScreen(
            state = CreateState(
                editMode = EditMode.ADD
            ),
            onAction = {}
        )
    }
}