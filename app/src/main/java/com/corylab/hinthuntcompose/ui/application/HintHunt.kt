package com.corylab.hinthuntcompose.ui.application

import android.app.Application
import android.content.Context
import com.corylab.hinthuntcompose.data.repository.Repository

class HintHunt : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        repository = Repository(applicationContext)

        val sharedPreferences = getSharedPreferences(
            "appPrefs",
            Context.MODE_PRIVATE
        )
        if (!sharedPreferences.contains("_first_launch")) {
            sharedPreferences.edit().putInt("size", 18).putInt("complexity", 0).putInt("teams_color", 0).apply()
            sharedPreferences.edit().putBoolean("_first_launch", false).apply()
        }
    }

    companion object {
        lateinit var context: Context
            private set
        lateinit var repository: Repository
    }
}