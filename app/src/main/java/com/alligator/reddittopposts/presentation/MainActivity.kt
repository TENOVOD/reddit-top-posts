package com.alligator.reddittopposts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alligator.reddittopposts.presentation.ui.screen.mainscreen.MainScreen
import com.alligator.reddittopposts.presentation.ui.theme.RedditTopPostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RedditTopPostsTheme {

                MainScreen()

            }
        }
    }
}