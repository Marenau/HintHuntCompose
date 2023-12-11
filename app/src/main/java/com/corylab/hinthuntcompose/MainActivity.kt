package com.corylab.hinthuntcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.corylab.hinthuntcompose.screens.Home
import com.corylab.hinthuntcompose.ui.theme.HintHuntComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HintHuntComposeTheme {
                Home()
            }
        }
    }
}