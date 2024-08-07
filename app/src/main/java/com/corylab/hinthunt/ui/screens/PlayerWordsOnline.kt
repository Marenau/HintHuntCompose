package com.corylab.hinthunt.ui.screens

import android.content.res.Configuration
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.corylab.hinthunt.R
import com.corylab.hinthunt.ui.dialog.DialogWithChoice
import com.corylab.hinthunt.ui.dialog.DialogWithText
import com.corylab.hinthunt.ui.theme.LightGray
import com.corylab.hinthunt.ui.theme.MainText
import com.corylab.hinthunt.ui.theme.White
import com.corylab.hinthunt.ui.viemodel.FirebaseViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerWordsOnline(
    navController: NavController,
    fbViewModel: FirebaseViewModel,
    data: String
) {
    val uniqueKey = rememberSaveable {
        mutableStateOf(data)
    }
    fbViewModel.initiateKey(uniqueKey.value)

    val words = fbViewModel.words.collectAsState()
    val firstNumOfCard = fbViewModel.firstNumOfCards.collectAsState()
    val secondNumOfCard = fbViewModel.secondNumOfCards.collectAsState()
    val firstScore = fbViewModel.firstScore.collectAsState()
    val secondScore = fbViewModel.secondScore.collectAsState()
    val turn = fbViewModel.turn.collectAsState()
    val winner = fbViewModel.winner.collectAsState()
    val colorsNums = fbViewModel.colorNums.collectAsState()
    val selectedColors = fbViewModel.selectedWords.collectAsState()
    val numOfColors = fbViewModel.teamsColors.collectAsState()


    val quantity =
        if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 6

    val wordTurn = stringResource(id = R.string.fragment_player_turn)
    val turnText = if (turn.value == 1) {
        rememberSaveable {
            mutableStateOf(
                StringBuilder().append("←").append(wordTurn).toString()
            )
        }
    } else {
        rememberSaveable {
            mutableStateOf(
                StringBuilder().append(wordTurn).append("→").toString()
            )
        }
    }

    val neutralColor = colorResource(id = R.color.neutral)

    val (firstTeamColor, secondTeamColor) = when (numOfColors.value) {
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

    val colorMap = mapOf(
        0 to neutralColor,
        1 to firstTeamColor,
        2 to secondTeamColor,
        3 to colorResource(id = R.color.black)
    )

    SideEffect {
        fbViewModel.getWords()
        fbViewModel.getSelectedColors()
        fbViewModel.getNumOfCards(1)
        fbViewModel.getNumOfCards(2)
        fbViewModel.getScore(1)
        fbViewModel.getScore(2)
        fbViewModel.getTurn()
        fbViewModel.getWinner()
        fbViewModel.getColorNums()
        fbViewModel.getTeamsColors()
    }

    val openHomeDialog = rememberSaveable { mutableStateOf(false) }

    val openWinnerDialog = rememberSaveable { mutableStateOf(false) }

    val openWinnerDialogControl = rememberSaveable { mutableStateOf(false) }

    if (winner.value == 0) {
        openWinnerDialogControl.value = false
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    if (firstScore.value == firstNumOfCard.value && firstScore.value != 0) {
        LaunchedEffect(key1 = null) {
            coroutineScope.launch {
                fbViewModel.putWinner(1)
                fbViewModel.putSelectedColor(List(words.value.size) { true })
            }
        }
    }

    if (secondScore.value == secondNumOfCard.value && secondScore.value != 0) {
        LaunchedEffect(key1 = null) {
            coroutineScope.launch {
                fbViewModel.putWinner(2)
                fbViewModel.putSelectedColor(List(words.value.size) { true })
            }
        }
    }

    if (winner.value != 0 && !openWinnerDialogControl.value) {
        openWinnerDialog.value = true
        if (winner.value == 1) {
            DialogWithText(
                openDialog = openWinnerDialog,
                title = stringResource(id = R.string.fragment_player_victory),
                text = stringResource(id = R.string.fragment_player_winner_first),
                buttonText = stringResource(id = R.string.fragment_player_confirm_winner),
                teamColor = firstTeamColor,
                buttonOnClick = { openWinnerDialogControl.value = true }
            )
        } else if (winner.value == 2) {
            DialogWithText(
                openDialog = openWinnerDialog,
                title = stringResource(id = R.string.fragment_player_victory),
                text = stringResource(id = R.string.fragment_player_winner_second),
                buttonText = stringResource(id = R.string.fragment_player_confirm_winner),
                teamColor = secondTeamColor,
                buttonOnClick = { openWinnerDialogControl.value = true }
            )
        }
    }

    if (openHomeDialog.value) {
        DialogWithChoice(
            onDismiss = { openHomeDialog.value = false },
            title = stringResource(id = R.string.fragment_player_warning),
            text = stringResource(id = R.string.fragment_player_confirm_return_home_text),
            firstButtonText = stringResource(id = R.string.fragment_player_confirm_return_home_no),
            secondButtonText = stringResource(id = R.string.fragment_player_confirm_return_home_yes),
            firstButtonOnClick = { openHomeDialog.value = false },
            secondButtonOnClick = {
                navController.navigate("home") {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            })
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = LightGray,
                    snackbarData = data,
                    contentColor = White,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
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
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { openHomeDialog.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_home),
                            contentDescription = "Home",
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
                            text = StringBuilder().append("${firstScore.value}/${firstNumOfCard.value}")
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
                            text = StringBuilder().append("${secondScore.value}/${secondNumOfCard.value}")
                                .toString(),
                            style = MainText,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            if (words.value.isNotEmpty() && selectedColors.value.isNotEmpty() && colorsNums.value.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    val count = words.value.size
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
                                            if (selectedColors.value[index]) {
                                                colorMap[colorsNums.value[index]]!!
                                            } else {
                                                colorResource(id = R.color.dark_gray)
                                            }
                                        )
                                        .combinedClickable(
                                            onClick = {
                                                if (snackbarHostState.currentSnackbarData == null) {
                                                    prevWord = ""
                                                }
                                                if (prevWord != words.value[index]) {
                                                    snackbarHostState.currentSnackbarData?.dismiss()
                                                    coroutineScope.launch {
                                                        prevWord = words.value[index]
                                                        snackbarHostState.showSnackbar(
                                                            message = words.value[index],
                                                            withDismissAction = true,
                                                            duration = SnackbarDuration.Indefinite
                                                        )
                                                    }
                                                }
                                            },
                                            onLongClick = {
                                                if (!selectedColors.value[index]) {
                                                    coroutineScope.launch {
                                                        fbViewModel.putSelectedColor(index, true)
                                                    }
                                                    if (colorsNums.value[index] == 0) {
                                                        coroutineScope.launch {
                                                            fbViewModel.putTurn(
                                                                when (turn.value) {
                                                                    1 -> 2
                                                                    else -> 1
                                                                }
                                                            )
                                                        }
                                                    }
                                                    if ((turn.value == 1 && colorsNums.value[index] == 2) || (turn.value == 2 && colorsNums.value[index] == 1)) {
                                                        coroutineScope.launch {
                                                            fbViewModel.putTurn(
                                                                when (turn.value) {
                                                                    1 -> 2
                                                                    else -> 1
                                                                }
                                                            )
                                                        }
                                                    }
                                                    if (colorsNums.value[index] == 1) {
                                                        coroutineScope.launch {
                                                            fbViewModel.putScore(
                                                                1,
                                                                firstScore.value + 1
                                                            )
                                                        }
                                                    }
                                                    if (colorsNums.value[index] == 2) {
                                                        coroutineScope.launch {
                                                            fbViewModel.putScore(
                                                                2,
                                                                secondScore.value + 1
                                                            )
                                                        }
                                                    }
                                                    if (colorsNums.value[index] == 3) {
                                                        coroutineScope.launch {
                                                            fbViewModel.putWinner(
                                                                when (turn.value) {
                                                                    1 -> 2
                                                                    else -> 1
                                                                }
                                                            )
                                                            fbViewModel.putSelectedColor(List(words.value.size) { true })
                                                        }
                                                    }
                                                }
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = words.value[index],
                                        modifier = Modifier
                                            .padding(vertical = 16.dp),
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = false,
                                        style = MainText,
                                        fontSize = 15.sp,
                                        color = if (selectedColors.value[index] && colorMap[colorsNums.value[index]]!! == neutralColor) {
                                            colorResource(id = R.color.dark_gray)
                                        } else colorResource(id = R.color.white)
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

    DisposableEffect(Unit) {
        onDispose {
            fbViewModel.cleanUp()
        }
    }
}