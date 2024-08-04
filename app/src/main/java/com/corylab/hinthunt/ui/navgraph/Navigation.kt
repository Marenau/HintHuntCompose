package com.corylab.hinthunt.ui.navgraph

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.corylab.hinthunt.ui.application.HintHunt
import com.corylab.hinthunt.ui.screens.Authors
import com.corylab.hinthunt.ui.screens.ConnectGame
import com.corylab.hinthunt.ui.screens.CreateGame
import com.corylab.hinthunt.ui.screens.Home
import com.corylab.hinthunt.ui.screens.LeaderWordsOffline
import com.corylab.hinthunt.ui.screens.LeaderWordsOnline
import com.corylab.hinthunt.ui.screens.PlayerWordsOffline
import com.corylab.hinthunt.ui.screens.PlayerWordsOnline
import com.corylab.hinthunt.ui.screens.Settings
import com.corylab.hinthunt.ui.viemodel.FirebaseViewModel
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel
import com.corylab.hinthunt.ui.viemodel.WordViewModel

@Composable
fun Navigation(applicationContext: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home")
    {
        composable(route = "leader_offline")
        {
            LeaderWordsOffline(
                navController = navController,
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                data = ""
            )
        }
        composable(
            route = "leader_offline/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            LeaderWordsOffline(
                navController = navController,
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                data = data
            )
        }
        composable(
            route = "player_offline/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            PlayerWordsOffline(
                navController = navController,
                data = data
            )
        }
        composable(
            route = "leader_online/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            LeaderWordsOnline(
                navController = navController,
                fbViewModel = viewModel(
                    factory = FirebaseViewModel.FirebaseViewModelFactory(
                        applicationContext,
                        HintHunt.onlineWordsRepository
                    )
                ),
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                data = data
            )
        }
        composable(
            route = "player_online/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        )
        {
            val data = it.arguments?.getString("data")!!
            PlayerWordsOnline(
                navController = navController,
                fbViewModel = viewModel(
                    factory = FirebaseViewModel.FirebaseViewModelFactory(
                        applicationContext,
                        HintHunt.onlineWordsRepository
                    )
                ),
                data = data
            )
        }
        composable("create_game") {
            CreateGame(
                navController = navController,
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                ),
                fbViewModel = viewModel(
                    factory = FirebaseViewModel.FirebaseViewModelFactory(
                        applicationContext,
                        HintHunt.onlineWordsRepository
                    )
                ),
                wViewModel = viewModel(
                    factory = WordViewModel.WordViewModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                )
            )
        }
        composable("settings") {
            Settings(
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                )
            )
        }
        composable("home") {
            Home(navController)
        }
        composable("connect_game") {
            ConnectGame(
                navController,
                spViewModel = viewModel(
                    factory = SharedPreferencesViewModel.SharedPreferencesModelFactory(
                        applicationContext,
                        HintHunt.offlineRepository
                    )
                )
            )
        }
        composable("authors") {
            Authors(navController)
        }
    }
}