package com.ykim.mememaster.presentation.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ykim.mememaster.R
import com.ykim.mememaster.presentation.components.MemeButton
import com.ykim.mememaster.presentation.components.MemeIcon
import com.ykim.mememaster.presentation.components.MemeOutlinedButton
import com.ykim.mememaster.ui.theme.MemeMasterTheme

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

@Preview
@Composable
private fun CreateScreenScreenPreview() {
    MemeMasterTheme {
        CreateScreen(
            state = CreateState(),
            onAction = {}
        )
    }
}