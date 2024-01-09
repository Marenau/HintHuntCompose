package com.corylab.hinthuntcompose.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateBoolListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateIntListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateNumsListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateWordListOf
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.viemodel.SharedPreferencesViewModel
import com.corylab.hinthuntcompose.ui.viemodel.WordViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LeaderWordsOffline(
    navController: NavController,
    wViewModel: WordViewModel,
    spViewModel: SharedPreferencesViewModel,
) {
    val words = rememberMutableStateWordListOf(wViewModel.getWords())

    val quantity =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

    val size = words.size
    val firstNumOfCard = rememberSaveable {
        when (words.size) {
            18 -> mutableIntStateOf(5)
            24 -> mutableIntStateOf(7)
            else -> mutableIntStateOf(9)
        }
    }

    val secondNumOfCard = rememberSaveable {
        when (words.size) {
            18 -> mutableIntStateOf(5)
            24 -> mutableIntStateOf(7)
            else -> mutableIntStateOf(9)
        }
    }

    val firstScore = rememberSaveable {
        mutableIntStateOf(0)
    }
    val secondScore = rememberSaveable {
        mutableIntStateOf(0)
    }

    val turn = rememberSaveable {
        if (Random.nextInt(2) == 1) {
            firstNumOfCard.intValue++
            mutableIntStateOf(1)
        } else {
            secondNumOfCard.intValue++
            mutableIntStateOf(2)
        }
    }

    val turnText = rememberSaveable {
        mutableStateOf(
            if (turn.intValue == 1) StringBuilder().append("←").append("Turn")
                .toString() else StringBuilder().append("Turn").append("→").toString()
        )
    }

    val colorsNums = rememberMutableStateNumsListOf(
        wViewModel.createColorsNums(
            size,
            firstNumOfCard.intValue,
            secondNumOfCard.intValue
        )
    )

    val neutralColor = colorResource(id = R.color.neutral)

    val (firstTeamColor, secondTeamColor) = when (spViewModel.getInt("teams_color")) {
        0 -> Pair(
            colorResource(id = R.color.wild_berries_color1),
            colorResource(id = R.color.wild_berries_color2)
        )

        1 -> Pair(
            colorResource(id = R.color.mustard_field_color1),
            colorResource(id = R.color.mustard_field_color2)
        )

        2 -> Pair(
            colorResource(id = R.color.carrot_freshness_color1),
            colorResource(id = R.color.carrot_freshness_color2)
        )

        3 -> Pair(
            colorResource(id = R.color.noble_saffron_color1),
            colorResource(id = R.color.noble_saffron_color2)
        )

        4 -> Pair(
            colorResource(id = R.color.lilac_at_midnight_color1),
            colorResource(id = R.color.lilac_at_midnight_color2)
        )

        else -> Pair(
            colorResource(id = R.color.cranberries_in_moss_color1),
            colorResource(id = R.color.cranberries_in_moss_color2)
        )
    }

    val selectedColors = rememberMutableStateIntListOf(size)
    val colorMap = mapOf(
        0 to neutralColor,
        1 to firstTeamColor,
        2 to secondTeamColor,
        3 to colorResource(id = R.color.black)
    )

    val showCards = rememberSaveable {
        mutableStateOf(false)
    }

    val enabled = rememberMutableStateBoolListOf(size)

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
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_show_cards),
                        contentDescription = "Show cards",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = {
                    turn.intValue = if (turn.intValue == 1) {
                        turnText.value = StringBuilder().append("Turn").append("→").toString()
                        2
                    } else {
                        turnText.value = StringBuilder().append("←").append("Turn").toString()
                        1
                    }
                    Log.i("PRESS", turn.intValue.toString())
                }) {
                    //TODO confirm window
                    Icon(
                        painter = painterResource(id = R.drawable.icon_turn),
                        contentDescription = "Turn",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = {
                    navController.popBackStack(
                        destinationId = navController.findDestination("home")!!.id,
                        inclusive = false
                    )
                }) {
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
                    firstScore.intValue = 0
                    secondScore.intValue = 0
                    selectedColors.fill(0)
                    words.clear()
                    words.addAll(wViewModel.getWords())
                    enabled.fill(true)
                    colorsNums.clear()
                    colorsNums.addAll(
                        wViewModel.createColorsNums(
                            size,
                            firstNumOfCard.intValue,
                            secondNumOfCard.intValue
                        )
                    )
                    val min = minOf(firstNumOfCard.intValue, secondNumOfCard.intValue)
                    firstNumOfCard.intValue = min
                    secondNumOfCard.intValue = min
                    turn.intValue = if (Random.nextInt(2) == 1) {
                        firstNumOfCard.intValue++
                        1
                    } else {
                        secondNumOfCard.intValue++
                        2
                    }
                    turnText.value =
                        if (turn.intValue == 1) StringBuilder().append("←").append("Turn")
                            .toString() else StringBuilder().append("Turn").append("→").toString()
                }) {
                    //TODO confirm window
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
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (firstTeamScore, textTurn, secondTeamScore) = createRefs()

                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(firstTeamColor)
                            .constrainAs(firstTeamScore) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start, 8.dp)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Text(
                            text = StringBuilder().append("${firstScore.intValue}/${firstNumOfCard.intValue}")
                                .toString(),
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Text(
                        text = turnText.value,
                        style = MainText,
                        modifier = Modifier.constrainAs(textTurn) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .shadow(
                                elevation = 3.dp,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clip(shape = RoundedCornerShape(6.dp))
                            .background(secondTeamColor)
                            .constrainAs(secondTeamScore) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end, 8.dp)
                                bottom.linkTo(parent.bottom)
                            }
                    ) {
                        Text(
                            text = StringBuilder().append("${secondScore.intValue}/${secondNumOfCard.intValue}")
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
                        for (j in 0 until quantity) {
                            val index = i
                            Box(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 10.dp,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .weight(1F)
                                    .padding(end = 8.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (showCards.value) {
                                            if (selectedColors[index] == 1)
                                                darkerColor(colorMap[colorsNums[index]]!!)
                                            else colorMap[colorsNums[index]]!!
                                        } else {
                                            if (selectedColors[index] == 1) {
                                                colorMap[colorsNums[index]]!!
                                            } else {
                                                colorResource(id = R.color.dark_gray)
                                            }
                                        }
                                    )
                                    .combinedClickable(
                                        onClick = {},
                                        onLongClick = {
                                            if (!showCards.value) {
                                                if (enabled[index]) {
                                                    enabled[index] = false
                                                    selectedColors[index] = 1
                                                    if (turn.intValue == 1) {
                                                        if (colorsNums[index] == 0) {
                                                            turn.intValue = 2
                                                            turnText.value = StringBuilder()
                                                                .append("Turn")
                                                                .append("→")
                                                                .toString()
                                                        } else if (colorsNums[index] == 1) {
                                                            firstScore.intValue++
                                                        } else if (colorsNums[index] == 2) {
                                                            secondScore.intValue++
                                                            turn.intValue = 2
                                                            turnText.value = StringBuilder()
                                                                .append("Turn")
                                                                .append("→")
                                                                .toString()
                                                        }
                                                    } else if (turn.intValue == 2) {
                                                        if (colorsNums[index] == 0) {
                                                            turn.intValue = 1
                                                            turnText.value = StringBuilder()
                                                                .append("←")
                                                                .append("Turn")
                                                                .toString()
                                                        } else if (colorsNums[index] == 1) {
                                                            firstScore.intValue++
                                                            turn.intValue = 1
                                                            turnText.value = StringBuilder()
                                                                .append("←")
                                                                .append("Turn")
                                                                .toString()
                                                        } else if (colorsNums[index] == 2) {
                                                            secondScore.intValue++
                                                        }
                                                    }
                                                }
                                            }
                                            Log.i("PRESS", turn.intValue.toString())
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = words[index],
                                    modifier = Modifier
                                        .padding(vertical = 16.dp),
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false,
                                    style = MainText,
                                    fontSize = 15.sp,
                                    color =
                                    if (showCards.value) {
                                        if (selectedColors[index] == 1)
                                            darkerColor(
                                                if (colorMap[colorsNums[index]]!! == neutralColor)
                                                    colorResource(id = R.color.dark_gray)
                                                else colorResource(id = R.color.white)
                                            )
                                        else {
                                            if (colorMap[colorsNums[index]]!! == neutralColor)
                                                colorResource(id = R.color.dark_gray)
                                            else colorResource(id = R.color.white)
                                        }
                                    } else {
                                        if (selectedColors[index] == 1 && colorMap[colorsNums[index]]!! == neutralColor) {
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

fun darkerColor(color: Color) =
    Color(ColorUtils.blendARGB(color.toArgb(), Color.Black.toArgb(), 0.5f))