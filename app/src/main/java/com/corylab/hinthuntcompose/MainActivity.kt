package com.corylab.hinthuntcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.corylab.hinthuntcompose.screens.CreateGame
import com.corylab.hinthuntcompose.screens.Home
import com.corylab.hinthuntcompose.screens.Leader
import com.corylab.hinthuntcompose.screens.Settings
import com.corylab.hinthuntcompose.ui.theme.HintHuntComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HintHuntComposeTheme {
                Navigation()
            }
        }
    }
}
