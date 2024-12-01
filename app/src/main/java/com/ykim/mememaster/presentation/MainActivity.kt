package com.ykim.mememaster.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ykim.mememaster.presentation.home.HomeEvent
import com.ykim.mememaster.presentation.home.HomeViewModel
import com.ykim.mememaster.presentation.navigation.NavigationRoot
import com.ykim.mememaster.ui.theme.MemeMasterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is HomeEvent.StartShareChooser -> startShareChooser(event.uriList)
                }
            }
        }

        setContent {
            MemeMasterTheme {
                val navController = rememberNavController()
                NavigationRoot(navController = navController)
            }
        }
    }

    private fun startShareChooser(uriList: ArrayList<Uri>) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            type = "image/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uriList))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(Intent.createChooser(shareIntent, "Share images"))
    }
}