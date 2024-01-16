package com.corylab.hinthuntcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.AppNameStyle
import com.corylab.hinthuntcompose.ui.theme.MainText


@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
            onClick = {  }
        )
        ButtonWithText(
            modifier = modifier,
            text = stringResource(id = R.string.fragment_home_settings),
            onClick = { navController.navigate("settings") }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ButtonWithText(
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(id = R.string.fragment_home_authors),
                onClick = { navController.navigate("authors") }
            )
        }
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
            containerColor = colorResource(id = R.color.light_gray),
            contentColor = colorResource(id = R.color.white)
        )
    ) {
        Text(text = text, style = MainText)
    }
}