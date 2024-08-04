package com.corylab.hinthunt.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.corylab.hinthunt.R
import com.corylab.hinthunt.ui.dialog.DialogWithChoice
import com.corylab.hinthunt.ui.dialog.DialogWithRemember
import com.corylab.hinthunt.ui.theme.MainText
import com.corylab.hinthunt.ui.theme.Title
import com.corylab.hinthunt.ui.viemodel.FirebaseViewModel
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel
import com.corylab.hinthunt.ui.viemodel.WordViewModel
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGame(
    navController: NavController,
    spViewModel: SharedPreferencesViewModel,
    fbViewModel: FirebaseViewModel,
    wViewModel: WordViewModel,
) {
    val scrollState = rememberScrollState()

    val mode = spViewModel.getInt("game_mode")
    val type = spViewModel.getInt("game_type")
    val size = spViewModel.getInt("size")
    val complexity = spViewModel.getInt("complexity")
    val teamsColor = spViewModel.getInt("teams_color")

    val cardModifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)

    val gameMods = listOf(
        stringResource(id = R.string.fragment_create_game_game_mode_offline),
        stringResource(id = R.string.fragment_create_game_game_mode_online)
    )
    val (modeSelectedOption, modeOnOptionSelected) = rememberSaveable {
        mutableStateOf(gameMods[mode])
    }

    val gameTypes = listOf(
        stringResource(id = R.string.fragment_create_game_game_type_words),
        stringResource(id = R.string.fragment_create_game_game_type_illustrations)
    )
    val (typeSelectedOption, typeOnOptionSelected) = rememberSaveable {
        mutableStateOf(gameTypes[type])
    }

    val openComplexity = rememberSaveable { mutableStateOf(true) }

    val openOffline = rememberSaveable { mutableStateOf(true) }

    if (typeSelectedOption == gameTypes[1]) {
        openComplexity.value = false
        openOffline.value = false
    } else {
        openComplexity.value = true
        openOffline.value = true
    }

    val sizes = listOf(
        stringResource(id = R.string.fragment_create_game_board_size_18),
        stringResource(id = R.string.fragment_create_game_board_size_24),
        stringResource(id = R.string.fragment_create_game_board_size_30)
    )
    val (sizeSelectedOption, sizeOnOptionSelected) = rememberSaveable {
        mutableStateOf(
            sizes[when (size) {
                18 -> 0
                24 -> 1
                else -> 2
            }]
        )
    }

    val complexities = listOf(
        stringResource(id = R.string.fragment_create_game_words_complexity_easy),
        stringResource(id = R.string.fragment_create_game_words_complexity_medium),
        stringResource(id = R.string.fragment_create_game_words_complexity_hard)
    )
    val (complexitySelectedOption, complexityOnOptionSelected) = rememberSaveable {
        mutableStateOf(
            complexities[complexity]
        )
    }

    val teamsColors = listOf(
        stringResource(id = R.string.fragment_create_game_wild_berries),
        stringResource(id = R.string.fragment_create_game_mustard_field),
        stringResource(id = R.string.fragment_create_game_carrot_freshness),
        stringResource(id = R.string.fragment_create_game_noble_saffron),
        stringResource(id = R.string.fragment_create_game_lilac_at_midnight),
        stringResource(id = R.string.fragment_create_game_cranberries_in_moss)
    )
    val (teamsColorsSelectedOption, teamsColorsOnOptionSelected) = rememberSaveable {
        mutableStateOf(teamsColors[teamsColor])
    }

    val openDialog = rememberSaveable { mutableStateOf(false) }

    //TODO
    if (openDialog.value) {
        if (spViewModel.getBoolean("internet_connection_dialog")) {
            DialogWithRemember(
                onDismiss = { openDialog.value = false },
                title = stringResource(id = R.string.fragment_create_game_online_game_title),
                text = stringResource(id = R.string.fragment_create_game_online_game),
                firstButtonText = stringResource(id = R.string.fragment_create_game_online_game_cancel),
                firstButtonOnClick = { openDialog.value = false },
                checkboxText = stringResource(id = R.string.fragment_create_game_show),
                secondButtonText = stringResource(id = R.string.fragment_create_game_online_game_confirm),
                secondButtonOnClick = {
                    openDialog.value = false
                    val uniqueKey = createOnlineGame(spViewModel)
                    spViewModel.putString("last_game_id", uniqueKey)
                    val words = wViewModel.getWords()
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
                    if (Random.nextInt(2) == 1) firstNumOfCard++ else secondNumOfCard++
                    val colorsNums =
                        wViewModel.createColorsNums(words.size, firstNumOfCard, secondNumOfCard)
                    val numOfColors = spViewModel.getInt("teams_color")
                    val gameLang = spViewModel.getInt("language")
                    val gameComplexity = spViewModel.getInt("complexity")
                    fbViewModel.createGame(
                        key = uniqueKey,
                        words = words,
                        firstNumOfCard = firstNumOfCard,
                        secondNumOfCard = secondNumOfCard,
                        colorsNums = colorsNums,
                        numOfColors = numOfColors,
                        complexity = gameComplexity,
                        lang = gameLang
                    )
                    navController.navigate("leader_online/${uniqueKey}")
                },
                checkboxAction = { spViewModel.putBoolean("internet_connection_dialog", false) }
            )
        } else {
            openDialog.value = false
            val uniqueKey = createOnlineGame(spViewModel)
            spViewModel.putString("last_game_id", uniqueKey)
            val words = wViewModel.getWords()
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
            if (Random.nextInt(2) == 1) firstNumOfCard++ else secondNumOfCard++
            val colorsNums =
                wViewModel.createColorsNums(words.size, firstNumOfCard, secondNumOfCard)
            val numOfColors = spViewModel.getInt("teams_color")
            val gameLang = spViewModel.getInt("language")
            val gameComplexity = spViewModel.getInt("complexity")
            fbViewModel.createGame(
                key = uniqueKey,
                words = words,
                firstNumOfCard = firstNumOfCard,
                secondNumOfCard = secondNumOfCard,
                colorsNums = colorsNums,
                numOfColors = numOfColors,
                complexity = gameComplexity,
                lang = gameLang
            )
            navController.navigate("leader_online/${uniqueKey}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.fragment_create_game_title),
            style = Title,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_game_mode),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Row(
                Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                for (i in gameMods.indices) {
                    if (openOffline.value || i == 1) {
                        FilterChip(
                            selected = (gameMods[i] == modeSelectedOption),
                            onClick = {
                                modeOnOptionSelected(gameMods[i])
                            },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1F)
                                .padding(end = 8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                MaterialTheme.colorScheme.primary,
                                selectedContainerColor = MaterialTheme.colorScheme.primary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = MaterialTheme.colorScheme.onPrimary,
                                selectedBorderColor = MaterialTheme.colorScheme.onSurface,
                                selectedBorderWidth = 1.dp
                            ),
                            label = {
                                Text(
                                    text = gameMods[i],
                                    style = MainText,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                            }
                        )
                    }
                }
            }
        }
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(6.dp)

        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_game_type),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Row(
                Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                for (i in gameTypes.indices) {
                    FilterChip(
                        selected = (gameTypes[i] == typeSelectedOption),
                        onClick = {
                            typeOnOptionSelected(gameTypes[i])
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = MaterialTheme.colorScheme.onPrimary,
                            selectedBorderColor = MaterialTheme.colorScheme.onSurface,
                            selectedBorderWidth = 1.dp
                        ),
                        label = {
                            Text(
                                text = gameTypes[i],
                                style = MainText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )
                        }
                    )
                }
            }
        }
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(6.dp)

        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_board_size),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            Row(
                Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                for (i in sizes.indices) {
                    FilterChip(
                        selected = (sizes[i] == sizeSelectedOption),
                        onClick = { sizeOnOptionSelected(sizes[i]) },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            selectedContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = MaterialTheme.colorScheme.onPrimary,
                            selectedBorderColor = MaterialTheme.colorScheme.onSurface,
                            selectedBorderWidth = 1.dp
                        ),
                        label = {
                            Text(
                                text = sizes[i],
                                style = MainText,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 8.dp),
                                textAlign = TextAlign.Center,
                                fontSize = 15.sp
                            )
                        }
                    )
                }
            }
        }
        if (openComplexity.value) {
            Card(
                modifier = cardModifier,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(6.dp)

            ) {
                Text(
                    text = stringResource(id = R.string.fragment_create_game_complexity_of_words),
                    style = MainText,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
                Row(
                    Modifier
                        .selectableGroup()
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    complexities.forEach { text ->
                        FilterChip(
                            selected = (text == complexitySelectedOption),
                            onClick = { complexityOnOptionSelected(text) },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1F)
                                .padding(end = 8.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                selectedContainerColor = MaterialTheme.colorScheme.primary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                borderColor = MaterialTheme.colorScheme.onPrimary,
                                selectedBorderColor = MaterialTheme.colorScheme.onSurface,
                                selectedBorderWidth = 1.dp
                            ),
                            label = {
                                Text(
                                    text = text,
                                    style = MainText,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 15.sp
                                )
                            }
                        )
                    }
                }
            }
        }
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
            shape = RoundedCornerShape(6.dp)
        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_colors_of_teams),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
            val brushes = listOf(
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.wild_berries_color1),
                        colorResource(R.color.wild_berries_color2)
                    )
                ),
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.mustard_field_color1),
                        colorResource(R.color.mustard_field_color2)
                    )
                ),
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.carrot_freshness_color1),
                        colorResource(R.color.carrot_freshness_color2)
                    )
                ),
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.noble_saffron_color1),
                        colorResource(R.color.noble_saffron_color2)
                    )
                ),
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.lilac_at_midnight_color1),
                        colorResource(R.color.lilac_at_midnight_color2)
                    )
                ),
                Brush.horizontalGradient(
                    listOf(
                        colorResource(id = R.color.cranberries_in_moss_color1),
                        colorResource(R.color.cranberries_in_moss_color2)
                    )
                )
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                var index = 0
                teamsColors.take(3).forEach { text ->
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable {
                            teamsColorsOnOptionSelected(text)
                        }
                        .background(
                            brush = brushes[index],
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            if (text == teamsColorsSelectedOption) BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface
                            ) else BorderStroke(1.dp, Color.Transparent),
                            RoundedCornerShape(6.dp)
                        )

                    )
                    {
                        Text(
                            text = text,
                            color = Color.White,
                            style = MainText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            fontSize = 12.sp
                        )
                    }
                    index += 1
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 8.dp)
                    .selectableGroup()
            ) {
                var index = 3
                teamsColors.takeLast(3).forEach { text ->
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp, bottom = 8.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable {
                            teamsColorsOnOptionSelected(text)
                        }
                        .background(
                            brush = brushes[index],
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            if (text == teamsColorsSelectedOption) BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface
                            ) else BorderStroke(1.dp, Color.Transparent),
                            RoundedCornerShape(6.dp)
                        )
                    )
                    {
                        Text(
                            text = text,
                            color = Color.White,
                            style = MainText,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            softWrap = false,
                            fontSize = 12.sp
                        )
                    }
                    index += 1
                }
            }


        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {
            Button(
                onClick = {
                    spViewModel.putInt(
                        "game_mode", when (modeSelectedOption) {
                            gameMods[0] -> 0
                            else -> 1
                        }
                    )
                    spViewModel.putInt(
                        "game_type", when (typeSelectedOption) {
                            gameTypes[0] -> 0
                            else -> 1
                        }
                    )
                    spViewModel.putInt("size", sizeSelectedOption.toInt())
                    spViewModel.putInt(
                        "complexity", when (complexitySelectedOption) {
                            complexities[0] -> 0
                            complexities[1] -> 1
                            else -> 2
                        }
                    )
                    spViewModel.putInt(
                        "teams_color", when (teamsColorsSelectedOption) {
                            teamsColors[0] -> 0
                            teamsColors[1] -> 1
                            teamsColors[2] -> 2
                            teamsColors[3] -> 3
                            teamsColors[4] -> 4
                            else -> 5
                        }
                    )
                    if (gameMods[1] == modeSelectedOption) {
                        openDialog.value = true
                    } else {
                        navController.navigate("leader_offline")
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(id = R.string.fragment_create_game_create),
                    style = MainText
                )
            }
        }
    }
}

fun createOnlineGame(viewModel: SharedPreferencesViewModel): String {
    val uniqueKey = generateUniqueKey()
    val currentTime = System.currentTimeMillis()
    val onlineGame = viewModel.getString("online_game")
    val newOnlineGame = if (onlineGame == "empty") {
        "$uniqueKey.$currentTime"
    } else {
        "$onlineGame; $uniqueKey.$currentTime"
    }
    viewModel.putString("online_game", newOnlineGame)
    return uniqueKey
}

fun generateUniqueKey(length: Int = 6): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { _ -> charPool[Random.nextInt(charPool.size)] }
        .joinToString("")
}