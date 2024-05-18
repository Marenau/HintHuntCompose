package com.corylab.hinthunt.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.corylab.hinthunt.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_condensed_extra_bold, FontWeight.ExtraBold),
    Font(R.font.roboto_condensed_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_condensed_medium, FontWeight.Medium),
    Font(R.font.roboto_condensed_regular, FontWeight.Normal)
)


val AppNameStyle: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
val Title: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

val MainText: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RobotoFontFamily,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp
        )
    }
