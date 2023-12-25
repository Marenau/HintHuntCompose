package com.corylab.hinthuntcompose.screens

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.DarkGray
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.viemodel.WordViewModel
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Leader(navController: NavController, mViewModel: WordViewModel) {
    var words = remember {
        mViewModel.getWords()
    }

    val orientation =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

    val size = words.size
    val colorsNum = Array(size) { 0 }
    var firstNumOfCard = when (words.size) {
        18 -> 5
        24 -> 7
        else -> 9
    }
    var secondNumOfCard = when (words.size) {
        18 -> 5
        24 -> 7
        else -> 9
    }
    val firstTurn = remember {
        mutableStateOf(0)
    }
    val firstScore = remember {
        mutableStateOf(0)
    }
    val secondScore = remember {
        mutableStateOf(0)
    }

    val rand = Random()
    firstTurn.value = if (rand.nextInt(2) == 1) {
        firstNumOfCard++
        1
    } else {
        secondNumOfCard++
        2
    }
    generateNum(colorsNum, 1, firstNumOfCard)
    generateNum(colorsNum, 2, secondNumOfCard)
    generateNum(colorsNum, 3, 1)

    /* Duplicated array */
    Log.i("color", colorsNum.joinToString())

    /* Need to optimise */
    val selectColors = MutableList(size) { colorResource(id = R.color.dark_gray) }
    val colors = MutableList(0) { colorResource(id = R.color.dark_gray) }
    val colorMap = mapOf(
        0 to colorResource(id = R.color.neutral),
        1 to colorResource(id = R.color.blue),
        2 to colorResource(id = R.color.red),
        3 to colorResource(id = R.color.black)
    )
    colorsNum.forEach { colors.add(colorMap[it]!!) }

    Log.i("colors", colors.joinToString())

    val showedColors = remember { mutableStateListOf<Color>() }
    showedColors.addAll(selectColors)

    val showCards = remember {
        mutableStateOf(false)
    }

    val enabled = remember {
        mutableStateListOf<Boolean>()
    }
    enabled.addAll(Array(30) { true })

    val selectEnable = MutableList(size) { true }

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
                IconButton(onClick = {
                    showCards.value = !showCards.value
                    if (showCards.value) {
                        enabled.fill(false)
                        showedColors.clear()
                        showedColors.addAll(colors)
                    } else {
                        enabled.clear()
                        enabled.addAll(selectEnable)
                        showedColors.clear()
                        showedColors.addAll(selectColors)
                    }
                }) {
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
                IconButton(onClick = {
                    if (showCards.value) {
                        showCards.value = false
                    }
                    firstScore.value = 0
                    secondScore.value = 0
                    selectColors.fill(DarkGray)
                    showedColors.clear()
                    showedColors.addAll(selectColors)
                    enabled.fill(true)
                    selectEnable.fill(true)
                    words = mViewModel.getWords()
                }) {
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
                            text = StringBuilder().append("${firstScore.value}/$firstNumOfCard")
                                .toString(),
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.fragment_leader_menu_turn),
                        style = MainText,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 8.dp)
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(colorResource(id = R.color.red))
                    ) {
                        Text(
                            text = StringBuilder().append("${secondScore.value}/$secondNumOfCard")
                                .toString(),
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                val count = words.size
                var i = 0
                while (i < count - 1) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        for (j in 0 until orientation) {
                            val index = i
                            Button(
                                onClick = {
                                    if (enabled[index]) {
                                        if (colorsNum[index] == 1)
                                            firstScore.value++
                                        else if (colorsNum[index] == 2)
                                            secondScore.value++
                                        enabled[index] = false
                                        selectEnable[index] = false
                                        selectColors[index] = colors[index]
                                        showedColors[index] = colors[index]
                                    }
                                },
                                modifier = Modifier
                                    .weight(1F)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                    if (showCards.value) {
                                        if (showedColors[index] == selectColors[index])
                                            darkerColor(showedColors[index])
                                        else showedColors[index]
                                    } else showedColors[index]
                                ),
                                shape = RoundedCornerShape(6.dp),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 4.dp,
                                    pressedElevation = 4.dp,
                                    focusedElevation = 4.dp,
                                    hoveredElevation = 4.dp,
                                    disabledElevation = 4.dp
                                ),
                                contentPadding = PaddingValues(start = 1.dp, end = 1.dp),
                            ) {
                                Text(
                                    text = words[index],
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false,
                                    style = MainText,
                                    fontSize = 15.sp,
                                    color =
                                    if (showCards.value) {
                                        if (showedColors[index] == selectColors[index])
                                            darkerColor(
                                                if (selectColors[index] == colorResource(id = R.color.neutral))
                                                    colorResource(id = R.color.dark_gray)
                                                else colorResource(id = R.color.white)
                                            )
                                        else {
                                            if (showedColors[index] == colorResource(id = R.color.neutral))
                                                colorResource(id = R.color.dark_gray)
                                            else colorResource(id = R.color.white)
                                        }
                                    } else {
                                        if (selectColors[index] == colorResource(id = R.color.neutral)) {
                                            colorResource(id = R.color.dark_gray)
                                        } else colorResource(id = R.color.white)
                                    }
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

fun generateNum(arr: Array<Int>, num: Int, size: Int) {
    val rand = Random()
    for (i in 1..size) {
        var index: Int
        do {
            index = rand.nextInt(arr.size)
        } while (arr[index] != 0)
        arr[index] = num
    }
}

fun darkerColor(color: Color) =
    Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.5f))