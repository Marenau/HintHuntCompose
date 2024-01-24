package com.corylab.hinthuntcompose.ui.screens

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.data.qrcode.generateQrCode
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateBoolListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateIntListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateNumsListOf
import com.corylab.hinthuntcompose.data.remember.rememberMutableStateWordListOf
import com.corylab.hinthuntcompose.ui.dialog.DialogWithChoice
import com.corylab.hinthuntcompose.ui.dialog.DialogWithImage
import com.corylab.hinthuntcompose.ui.dialog.DialogWithText
import com.corylab.hinthuntcompose.ui.theme.LightGray
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.theme.White
import com.corylab.hinthuntcompose.ui.viemodel.SharedPreferencesViewModel
import com.corylab.hinthuntcompose.ui.viemodel.WordViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LeaderWordsOffline(
    navController: NavController,
    wViewModel: WordViewModel,
    spViewModel: SharedPreferencesViewModel,
    data: String
) {
    val localContext = LocalContext.current

    val words = rememberMutableStateWordListOf(
        if (data.isNotEmpty()) {
            data.substring(0, data.indexOfFirst { it == ';' }).split(',')
        } else wViewModel.getWords()
    )

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

    val wordTurn = stringResource(id = R.string.fragment_leader_turn)
    val turnText = rememberSaveable {
        mutableStateOf(
            if (turn.intValue == 1) StringBuilder().append("←").append(wordTurn)
                .toString() else StringBuilder().append(wordTurn).append("→").toString()
        )
    }

    val colorsNums = rememberMutableStateNumsListOf(
        if (data.isNotEmpty()) {
            data.substring(data.indexOfFirst { it == ';' } + 1, data.indexOfLast { it == ';' })
                .split(',').map { it.toInt() }
        } else wViewModel.createColorsNums(size, firstNumOfCard.intValue, secondNumOfCard.intValue)
    )

    if (data.isNotEmpty()) {
        firstNumOfCard.intValue = colorsNums.count { it == 1 }
        secondNumOfCard.intValue = colorsNums.count { it == 2 }
    }

    val neutralColor = colorResource(id = R.color.neutral)
    val numOfColors =
        if (data.isNotEmpty()) data[data.length - 1].digitToInt() else spViewModel.getInt("teams_color")
    val (firstTeamColor, secondTeamColor) = when (numOfColors) {
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

    val openDialog = rememberSaveable { mutableStateOf(false) }

    val openShuffleDialog = rememberSaveable { mutableStateOf(false) }

    val openHomeDialog = rememberSaveable { mutableStateOf(false) }

    val openWinnerDialog = rememberSaveable { mutableStateOf(false) }

    val blackPress = rememberSaveable { mutableStateOf(false) }

    if (openDialog.value) {
        val image = generateQrCode(
            StringBuilder().append(words.joinToString(separator = ",")).append(";")
                .append(colorsNums.joinToString(separator = ",")).append(";")
                .append(spViewModel.getInt("teams_color")).toString()
        )!!
        DialogWithImage(openDialog, image)
    }

    if (firstScore.intValue == firstNumOfCard.intValue) {
        if (openWinnerDialog.value) {
            enabled.fill(false)
            DialogWithText(
                openDialog = openWinnerDialog,
                title = stringResource(id = R.string.fragment_leader_victory),
                text = stringResource(id = R.string.fragment_leader_winner_first),
                buttonText = stringResource(id = R.string.fragment_leader_confirm_winner),
                teamColor = firstTeamColor
            )
        }
    }

    if (secondScore.intValue == secondNumOfCard.intValue) {
        if (openWinnerDialog.value) {
            enabled.fill(false)
            DialogWithText(
                openDialog = openWinnerDialog,
                title = stringResource(id = R.string.fragment_leader_victory),
                text = stringResource(id = R.string.fragment_leader_winner_second),
                buttonText = stringResource(id = R.string.fragment_leader_confirm_winner),
                teamColor = secondTeamColor
            )
        }
    }

    if (blackPress.value) {
        if (openWinnerDialog.value) {
            val winner: String
            val winnerColor: Color
            if (turn.intValue == 1) {
                winner = stringResource(id = R.string.fragment_leader_winner_second)
                winnerColor = secondTeamColor
            } else {
                winner = stringResource(id = R.string.fragment_leader_winner_first)
                winnerColor = firstTeamColor
            }
            enabled.fill(false)
            DialogWithText(
                openDialog = openWinnerDialog,
                title = stringResource(id = R.string.fragment_leader_defeat),
                text = winner,
                buttonText = stringResource(id = R.string.fragment_leader_confirm_winner),
                teamColor = winnerColor
            )
        }
    }

    if (openHomeDialog.value) {
        DialogWithChoice(
            onDismiss = { openHomeDialog.value = false },
            title = stringResource(id = R.string.fragment_leader_warning),
            text = stringResource(id = R.string.fragment_leader_confirm_return_home_text),
            firstButtonText = stringResource(id = R.string.fragment_leader_confirm_return_home_no),
            secondButtonText = stringResource(id = R.string.fragment_leader_confirm_return_home_yes),
            firstButtonOnClick = { openHomeDialog.value = false },
            secondButtonOnClick = {
                navController.popBackStack()
                navController.navigate("home")
                navController.popBackStack()
            })
    }

    if (openShuffleDialog.value) {
        DialogWithChoice(
            onDismiss = { openShuffleDialog.value = false },
            title = stringResource(id = R.string.fragment_leader_warning),
            text = stringResource(id = R.string.fragment_leader_confirm_shuffle_text),
            firstButtonText = stringResource(id = R.string.fragment_leader_confirm_shuffle_no),
            secondButtonText = stringResource(id = R.string.fragment_leader_confirm_shuffle_yes),
            firstButtonOnClick = { openShuffleDialog.value = false },
            secondButtonOnClick = {
                if (showCards.value) showCards.value = false
                firstScore.intValue = 0
                secondScore.intValue = 0
                blackPress.value = false
                selectedColors.fill(0)
                words.clear()
                words.addAll(wViewModel.getWords())
                enabled.fill(true)
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
                    if (turn.intValue == 1) StringBuilder().append("←").append(wordTurn)
                        .toString() else StringBuilder().append(wordTurn).append("→")
                        .toString()
                colorsNums.clear()
                colorsNums.addAll(
                    wViewModel.createColorsNums(
                        size,
                        firstNumOfCard.intValue,
                        secondNumOfCard.intValue
                    )
                )
                openShuffleDialog.value = false
            }
        )
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = LightGray,
                    snackbarData = data,
                    contentColor = White,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                )
            }
        },
        bottomBar = {
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
                    updateTurnText(turn, turnText, wordTurn, true)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_turn),
                        contentDescription = "Turn",
                        tint = colorResource(id = R.color.white)
                    )
                }
                IconButton(onClick = { openHomeDialog.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_home),
                        contentDescription = "Home",
                        tint = colorResource(id = R.color.white)
                    )
                }
                if (data.isEmpty()) {
                    IconButton(onClick = { openShuffleDialog.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_mix_cards),
                            contentDescription = "Mix cards",
                            tint = colorResource(id = R.color.white)
                        )
                    }
                }

                IconButton(onClick = {
                    openDialog.value = true
                }) {
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
                var prevWord = ""
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
                                        onClick = {
                                            if (snackbarHostState.currentSnackbarData == null) {
                                                prevWord = ""
                                            }
                                            if (prevWord != words[index]) {
                                                snackbarHostState.currentSnackbarData?.dismiss()
                                                coroutineScope.launch {
                                                    prevWord = words[index]
                                                    snackbarHostState.showSnackbar(
                                                        message = words[index],
                                                        withDismissAction = true,
                                                        duration = SnackbarDuration.Indefinite
                                                    )
                                                }
                                            }
                                        },
                                        onLongClick = {
                                            if (!showCards.value) {
                                                if (enabled[index]) {
                                                    enabled[index] = false
                                                    selectedColors[index] = 1
                                                    if (colorsNums[index] == 0) {
                                                        updateTurnText(
                                                            turn,
                                                            turnText,
                                                            wordTurn,
                                                            true
                                                        )
                                                    }
                                                    if ((turn.intValue == 1 && colorsNums[index] == 2) || (turn.intValue == 2 && colorsNums[index] == 1)) {
                                                        updateTurnText(
                                                            turn,
                                                            turnText,
                                                            wordTurn,
                                                            true
                                                        )
                                                    }
                                                    firstScore.intValue += if (colorsNums[index] == 1) 1 else 0
                                                    if (firstScore.intValue == firstNumOfCard.intValue) openWinnerDialog.value =
                                                        true
                                                    secondScore.intValue += if (colorsNums[index] == 2) 1 else 0
                                                    if (secondScore.intValue == secondNumOfCard.intValue) openWinnerDialog.value =
                                                        true

                                                    if (colorsNums[index] == 3) {
                                                        blackPress.value = true
                                                        openWinnerDialog.value = true
                                                    }
                                                }
                                            }
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

fun updateTurnText(
    turn: MutableIntState,
    turnText: MutableState<String>,
    text: String,
    change: Boolean
) {
    if (turn.intValue == 1) {
        turnText.value = StringBuilder().append(text).append("→").toString()
        if (change) turn.intValue = 2
    } else {
        turnText.value = StringBuilder().append("←").append(text).toString()
        if (change) turn.intValue = 1
    }
}