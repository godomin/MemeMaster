package com.ykim.mememaster.presentation.create

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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.components.MemeButton
import com.ykim.mememaster.presentation.components.MemeIcon
import com.ykim.mememaster.presentation.components.MemeOutlinedButton
import com.ykim.mememaster.presentation.components.MemeSlider
import com.ykim.mememaster.presentation.util.getTextComposable
import com.ykim.mememaster.ui.theme.MemeMasterTheme
import com.ykim.mememaster.ui.theme.SurfaceContainerHighDark

@Composable
fun CreateScreenRoot(
    navController: NavController,
    viewModel: CreateViewModel = hiltViewModel(),
) {
    CreateScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CreateScreen(
    state: CreateState,
    onAction: (CreateAction) -> Unit
) {
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
                    onClick = { /*TODO*/ },
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
                    painter = rememberAsyncImagePainter(R.drawable.template_06),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Divider()
            if (state.editMode == EditMode.ADD) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(68.dp)
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
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    when (state.editMode) {
                        EditMode.FONT -> {
                            LazyRow(
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
                                            state.selectedFont == memeFont.data -> Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(SurfaceContainerHighDark)
                                                .padding(8.dp)

                                            else -> Modifier
                                                .padding(8.dp)
                                        },
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        memeFont.data.getTextComposable(memeFont)
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = memeFont.data.getDisplayedName(),
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
                                    onValueChange = { onAction(CreateAction.OnTextFontSizeChanged(it)) },
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
                                    .padding(16.dp)
                            ) {
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
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
                        .padding(start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MemeIcon(
                        icon = Icons.Default.Close,
                        onClick = { /*TODO*/ }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ToolbarIcon(
                            selected = state.editMode == EditMode.FONT,
                            onClick = { /*TODO*/ }
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
                            onClick = { /*TODO*/ }
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
                            onClick = { /*TODO*/ }
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
                        onClick = { /*TODO*/ }
                    )
                }
            }
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
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.05f)
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
                editMode = EditMode.COLOR
            ),
            onAction = {}
        )
    }
}