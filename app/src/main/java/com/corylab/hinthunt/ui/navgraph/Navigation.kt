package com.corylab.hinthunt.ui.navgraph

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.corylab.hinthunt.ui.screens.CreateGame
import com.corylab.hinthunt.ui.screens.Home
import com.corylab.hinthunt.ui.screens.Settings
import com.corylab.hinthunt.ui.application.HintHunt
import com.corylab.hinthunt.ui.screens.Authors
import com.corylab.hinthunt.ui.screens.ConnectGame
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel
import com.corylab.hinthunt.ui.viemodel.WordViewModel
import com.corylab.hinthunt.ui.screens.LeaderWordsOffline
import com.corylab.hinthunt.ui.screens.PlayerWordsOffline

@Composable
fun Navigation(applicationContext: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home")
    {
        composable(route = "leader")
        {
            LeaderWordsOffline(
                navController = navController,
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                ),
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                ),
                data = ""
            )
        }
        composable(
            route = "leader/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            LeaderWordsOffline(
                navController = navController,
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                ),
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                ),
                data = data
            )
        }
        composable(
            route = "player/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            PlayerWordsOffline(
                navController = navController,
                data = data
            )
        }
        composable("create_game") {
            CreateGame(
                navController = navController,
                mViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                )
            )
        }
        composable("settings") {
            Settings(
                navController = navController,
                mViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.repository
                    )
                )
            )
        }
        composable("home") {
            Home(navController)
        }
        composable("connect_game") {
            ConnectGame(navController)
        }
        composable("authors") {
            Authors(navController)
        }
    }
}