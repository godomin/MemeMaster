package com.ykim.mememaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ykim.mememaster.presentation.create.CreateScreenRoot
import com.ykim.mememaster.presentation.home.HomeScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Create) {
        composable<Home> {
            HomeScreenRoot(navController = navController)
        }
        composable<Create> {
            CreateScreenRoot(navController = navController)
        }
    }
}