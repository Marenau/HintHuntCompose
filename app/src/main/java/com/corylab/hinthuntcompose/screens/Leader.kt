package com.corylab.hinthuntcompose.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.MainText

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Leader() {
    val context = LocalContext.current
    var words = remember {
        mutableStateOf(getWords(context))
    }
    Scaffold(bottomBar = {
        BottomAppBar(
            modifier = Modifier
                .height(60.dp),
            containerColor = colorResource(id = R.color.light_gray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_show_cards),
                        contentDescription = "Show cards",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_home),
                        contentDescription = "Home",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = { words.value = getWords(context) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_mix_cards),
                        contentDescription = "Mix cards",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_show_qr_code),
                        contentDescription = "Show QR code",
                        tint = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.background_color))
        ) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
                shape = RoundedCornerShape(6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 8.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(colorResource(id = R.color.blue))
                    ) {
                        Text(
                            text = "0/7",
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.fragment_leader_menu_turn),
                        style = MainText,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 20.sp
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 8.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(colorResource(id = R.color.red))
                    ) {
                        Text(
                            text = "0/7",
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                var count = words.value.size
                var i = 0
                while (i < count - 1) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        for (j in 0..2) {
                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.light_gray),
                                    contentColor = colorResource(id = R.color.white)
                                ),
                                shape = RoundedCornerShape(6.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp,
                                    pressedElevation = 4.dp,
                                    focusedElevation = 4.dp,
                                    hoveredElevation = 4.dp,
                                    disabledElevation = 4.dp
                                ),
                                contentPadding = PaddingValues(start = 1.dp, end = 1.dp)
                            ) {
                                Text(
                                    text = words.value[i],
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false,
                                    style = MainText,
                                    fontSize = 15.sp
                                )
                            }
                            i++
                        }
                    }
                }
            }
        }
    }
}

fun getWords(context: Context) = context.resources.openRawResource(R.raw.hard_words).bufferedReader().readLines().shuffled().take(30)