package com.corylab.hinthunt.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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

    private lateinit var themeObserver: ThemeObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        themeObserver = ThemeObserver(this, handler)
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION),
            false,
            themeObserver
        )
        setContent {
            HintHuntComposeTheme(darkTheme = isSystemInDarkTheme()) {
                Navigation(applicationContext)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(themeObserver)
    }
}

class ThemeObserver(private val context: Context, handler: Handler) : ContentObserver(handler) {
    override fun onChange(selfChange: Boolean) {
        val nightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newNightMode = when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_NO
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(newNightMode)
    }
}
