package com.corylab.hinthuntcompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.AppNameStyle
import com.corylab.hinthuntcompose.ui.theme.MainText

@Preview
@Composable
fun Home() {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.dark_gray),
        contentColor = colorResource(id = R.color.white)
    )
    val cornerShape = RoundedCornerShape(6.dp)
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
                .padding(top = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.fragment_home_app_name),
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            style = AppNameStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = { /* Handle create game click */ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
                .padding(top = 16.dp),
            shape = cornerShape,
            colors = buttonColors
        ) {
            Text(text = stringResource(id = R.string.fragment_home_create_game), style = MainText)
        }

        Button(
            onClick = { /* Handle connect game click */ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
                .padding(top = 8.dp),
            shape = cornerShape,
            colors = buttonColors
        ) {
            Text(text = stringResource(id = R.string.fragment_home_connect_game), style = MainText)
        }

        Button(
            onClick = { /* Handle rules click */ },
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            shape = cornerShape,
            colors = buttonColors
        ) {
            Text(text = stringResource(id = R.string.fragment_home_rules), style = MainText)
        }

        Button(
            onClick = { /* Handle settings click */ },
            modifier = Modifier
                .width(250.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            shape = cornerShape,
            colors = buttonColors
        ) {
            Text(text = stringResource(id = R.string.fragment_home_settings), style = MainText)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = { /* Handle authors click */ },
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.BottomCenter)
                    .padding(top = 8.dp),
                shape = cornerShape,
                colors = buttonColors
            ) {
                Text(text = stringResource(id = R.string.fragment_home_authors), style = MainText)
            }
        }
    }
}