package com.corylab.hinthunt.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.corylab.hinthunt.ui.application.HintHunt
import com.corylab.hinthunt.ui.navgraph.Navigation
import com.corylab.hinthunt.ui.theme.HintHuntComposeTheme
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: SharedPreferencesViewModel by viewModels {
        SharedPreferencesViewModel.SharedPreferencesModelFactory(
            this.applicationContext,
            HintHunt.offlineRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = viewModel.getInt("theme")
        AppCompatDelegate.setDefaultNightMode(
            when (theme) {
                0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                1 -> AppCompatDelegate.MODE_NIGHT_NO
                2 -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
        setContent {
            HintHuntComposeTheme(darkTheme = theme == 2) {
                Navigation(applicationContext)
            }
        }
    }
}
