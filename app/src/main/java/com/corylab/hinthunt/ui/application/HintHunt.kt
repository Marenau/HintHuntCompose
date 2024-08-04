package com.corylab.hinthunt.ui.application

import android.app.Application
import android.content.Context
import com.corylab.hinthunt.data.repository.OfflineRepository
import com.corylab.hinthunt.data.repository.OnlineWordsRepository
import java.util.Locale

class HintHunt : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        offlineRepository = OfflineRepository(applicationContext)
        onlineWordsRepository = OnlineWordsRepository()

        val sharedPreferences = getSharedPreferences(
            "appPrefs",
            Context.MODE_PRIVATE
        )
        if (!sharedPreferences.contains("_first_launch")) {
            sharedPreferences.edit()
                .putInt("language", if (Locale.getDefault().language == "en") 0 else 1)
                .putInt("words_language", if (Locale.getDefault().language == "en") 0 else 1)
                .putInt("words_size", 30)
                .putInt("words_complexity", 0)
                .putInt("size", 18)
                .putInt("complexity", 0)
                .putInt("teams_color", 0)
                .putInt("theme", 0)
                .putInt("game_mode", 0)
                .putInt("game_type", 0)
                .putBoolean("internet_connection_dialog", true)
                .apply()

            sharedPreferences.edit().putBoolean("_first_launch", false).apply()
        }
    }

    companion object {
        lateinit var context: Context
            private set
        lateinit var offlineRepository: OfflineRepository
        lateinit var onlineWordsRepository: OnlineWordsRepository
    }
}