package com.ykim.mememaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {

        }
    }
}