package com.corylab.hinthunt.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.corylab.hinthunt.ui.navgraph.Navigation
import com.corylab.hinthunt.ui.theme.HintHuntComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HintHuntComposeTheme {
                Navigation(applicationContext)
            }
        }
    }
}
