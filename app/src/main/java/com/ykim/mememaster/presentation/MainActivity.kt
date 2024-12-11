package com.ykim.mememaster.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.ykim.mememaster.presentation.navigation.NavigationRoot
import com.ykim.mememaster.ui.theme.MemeMasterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MemeMasterTheme {
                val navController = rememberNavController()
                NavigationRoot(navController = navController)
            }
        }
    }
}