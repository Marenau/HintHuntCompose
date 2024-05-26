package com.corylab.hinthunt.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.corylab.hinthunt.R
import com.corylab.hinthunt.ui.theme.AppNameStyle
import com.corylab.hinthunt.ui.theme.MainText


@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .wrapContentSize()
                .padding(top = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.fragment_home_app_name),
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            style = AppNameStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        val modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(250.dp)
            .padding(top = 16.dp)
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_create_game),
            onClick = { navController.navigate("create_game") }
        )
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_connect_game),
            onClick = { navController.navigate("connect_game") }
        )
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_rules),
            onClick = { val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Marenau/HintHuntCompose/wiki"
            ))
                context.startActivity(intent) }
        )
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_settings),
            onClick = { navController.navigate("settings") }
        )
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_authors),
            onClick = { navController.navigate("authors") }
        )
    }
}

@Composable
fun ButtonWithText(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(text = text, style = MainText)
    }
}