package com.ykim.mememaster.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ykim.mememaster.presentation.create.CreateScreenRoot
import com.ykim.mememaster.presentation.home.HomeScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreenRoot(
                onTemplateSelected = { templateResId ->
                    navController.navigate(Create(templateResId))
                }
            )
        }
        composable<Create> {
            CreateScreenRoot(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}