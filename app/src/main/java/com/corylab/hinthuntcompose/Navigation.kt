package com.corylab.hinthuntcompose

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.corylab.hinthuntcompose.screens.CreateGame
import com.corylab.hinthuntcompose.screens.Home
import com.corylab.hinthuntcompose.screens.Leader
import com.corylab.hinthuntcompose.screens.Settings
@Composable
fun Navigation()
{

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home")
    {

        composable("leader")
        {
            Leader(navController)
        }
        composable("creategame")
        {
            CreateGame(navController)
        }
        composable("settings")
        {
            Settings(navController)
        }
        composable("home")
        {
            Home(navController)
        }
    }
}