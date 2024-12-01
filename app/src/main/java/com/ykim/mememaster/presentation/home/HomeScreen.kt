package com.ykim.mememaster.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

}

@Preview
@Composable
private fun HomeScreenPreview() {
    MemeMasterTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}