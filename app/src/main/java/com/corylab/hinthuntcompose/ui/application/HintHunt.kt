package com.corylab.hinthuntcompose.ui.application

import android.app.Application
import android.content.Context
import com.corylab.hinthuntcompose.data.repository.Repository

class HintHunt : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        repository = Repository(applicationContext)

        //TODO
        val sharedPreferences = getSharedPreferences(
            "appPrefs",
            Context.MODE_PRIVATE
        )

        sharedPreferences.edit().putInt("size", 18).apply()
        sharedPreferences.edit().putInt("complexity", 1).apply()
    }

    companion object {
        lateinit var context: Context
            private set
        lateinit var repository: Repository
    }
}