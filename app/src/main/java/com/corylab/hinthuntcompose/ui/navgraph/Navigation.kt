package com.corylab.hinthuntcompose.ui.navgraph

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.corylab.hinthuntcompose.screens.CreateGame
import com.corylab.hinthuntcompose.screens.Home
import com.corylab.hinthuntcompose.screens.Leader
import com.corylab.hinthuntcompose.screens.Settings
import com.corylab.hinthuntcompose.ui.application.HintHunt
import com.corylab.hinthuntcompose.ui.viemodel.WordViewModel

@Composable
fun Navigation(applicationContext: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home")
    {
        composable("leader")
        {
            Leader(
                navController = navController,
                mViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                )
            )
        }
        composable("creategame") {
            CreateGame(navController)
        }
        composable("settings") {
            Settings(navController)
        }
        composable("home") {
            Home(navController)
        }
    }
}