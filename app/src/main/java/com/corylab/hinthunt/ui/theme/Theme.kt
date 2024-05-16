package com.corylab.hinthunt.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkGray,  //button color
    secondary = LightGray, //background for cards
    background = BackgroundColor,//background
    onSurface = White, //text and border
    onPrimary = BorderDark,
    surface = DarkGray


)

private val LightColorScheme = lightColorScheme(
    primary = DarkFair, //button color
    secondary = LightFair,//background for cards
    background = BackgroundColorLight,//background
    onSurface = FontLightTheme, //text and border
    onPrimary = BorderLight,
    surface = CardsColorLight

)

@Composable
fun HintHuntComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    val window = (view.context as Activity).window
    window?.decorView?.rootView?.setBackgroundColor(colorScheme.background.toArgb())
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}