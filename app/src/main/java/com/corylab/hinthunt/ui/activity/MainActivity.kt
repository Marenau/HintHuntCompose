package com.corylab.hinthunt.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.corylab.hinthunt.ui.application.HintHunt
import com.corylab.hinthunt.ui.navgraph.Navigation
import com.corylab.hinthunt.ui.theme.HintHuntComposeTheme
import com.corylab.hinthunt.ui.viemodel.FirebaseViewModel
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val spViewModel: SharedPreferencesViewModel by viewModels {
        SharedPreferencesViewModel.SharedPreferencesModelFactory(
            this.applicationContext,
            HintHunt.offlineRepository
        )
    }

    private val fbViewModel: FirebaseViewModel by viewModels {
        FirebaseViewModel.FirebaseViewModelFactory(
            this.applicationContext,
            HintHunt.onlineWordsRepository
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val theme = spViewModel.getInt("theme")

        val splashScreen = installSplashScreen()

        val keepSplashScreenOn = MutableStateFlow(true)
        lifecycleScope.launch {
            val roomsString = spViewModel.getString("online_game")
            if (roomsString != "empty") {
                val rooms = mutableMapOf<String, Long>()
                val roomsList = roomsString!!.split("; ")
                for (roomString in roomsList) {
                    val roomParts = roomString.split(".")
                    if (roomParts.size == 2) {
                        val roomName = roomParts[0]
                        val roomDate = roomParts[1].toLongOrNull()
                        if (roomDate != null) {
                            rooms[roomName] = roomDate
                        }
                    }
                }
                val currentTime = System.currentTimeMillis()
                val threeDaysInMillis = 1 * 24 * 60 * 60 * 1000L
                val undeletedRooms = mutableMapOf<String, Long>()
                for ((room, time) in rooms) {
                    if (currentTime - time >= threeDaysInMillis) {
                        fbViewModel.dropRoom(room) {
                            undeletedRooms[room] = time
                        }
                    }
                }
                if (undeletedRooms.isEmpty().not()) {
                    val undeletedRoomsString = ""
                    for ((room, time) in undeletedRooms) {
                        undeletedRoomsString.plus(
                            if (undeletedRoomsString.isEmpty()) {
                                room
                            } else {
                                " $room"
                            }
                        )
                        undeletedRoomsString.plus(".")
                        undeletedRoomsString.plus(time)
                    }
                    spViewModel.putString("online_game", undeletedRoomsString)
                }
            }
            keepSplashScreenOn.value = false
        }

        splashScreen.setKeepOnScreenCondition {
            keepSplashScreenOn.value
        }

        setContent {
            HintHuntComposeTheme(theme = theme) {
                Navigation(applicationContext)
            }
        }
    }
}