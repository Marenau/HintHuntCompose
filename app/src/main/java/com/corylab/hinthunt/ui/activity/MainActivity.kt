package com.corylab.hinthunt.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.database.ContentObserver
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = viewModel.getInt("theme")
        AppCompatDelegate.setDefaultNightMode(
            when (theme) {
                0 -> AppCompatDelegate.MODE_NIGHT_NO
                1 -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
        setContent {
            HintHuntComposeTheme(darkTheme = theme == 1) {
                Navigation(applicationContext)
            }
        }
    }
}





