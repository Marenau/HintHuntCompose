package com.corylab.hinthuntcompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.corylab.hinthuntcompose.R

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto_condensed_extra_bold, FontWeight.ExtraBold),
    Font(R.font.roboto_condensed_semi_bold, FontWeight.SemiBold),
    Font(R.font.roboto_condensed_medium, FontWeight.Medium),
    Font(R.font.roboto_condensed_regular, FontWeight.Normal)
)

val AppNameStyle = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.SemiBold,
    color = White
)

val Title = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium,
    color = White
)

val MainText = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Normal,
    color = White,
    fontSize = 20.sp
)