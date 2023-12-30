package com.corylab.hinthuntcompose.ui.screens

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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.theme.Title
import com.corylab.hinthuntcompose.ui.viemodel.SharedPreferencesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGame(navController: NavController, mViewModel: SharedPreferencesViewModel) {

    val mode = mViewModel.getInt("game_mode")
    val type = mViewModel.getInt("game_type")
    val size = mViewModel.getInt("size")
    val complexity = mViewModel.getInt("complexity")
    val teamsColor = mViewModel.getInt("teams_color")

    val cardModifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)

    val gameMods = listOf(
        stringResource(id = R.string.fragment_create_game_game_mode_offline),
        stringResource(id = R.string.fragment_create_game_game_mode_online)
    )
    val (modeSelectedOption, modeOnOptionSelected) = remember {
        mutableStateOf(gameMods[mode])
    }

    val gameType = listOf(
        stringResource(id = R.string.fragment_create_game_game_type_words),
        stringResource(id = R.string.fragment_create_game_game_type_illustrations)
    )
    val (typeSelectedOption, typeOnOptionSelected) = remember {
        mutableStateOf(gameType[type])
    }

    val sizes = listOf(
        stringResource(id = R.string.fragment_create_game_board_size_18),
        stringResource(id = R.string.fragment_create_game_board_size_24),
        stringResource(id = R.string.fragment_create_game_board_size_30)
    )
    val (sizeSelectedOption, sizeOnOptionSelected) = remember {
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
    val (complexitySelectedOption, complexityOnOptionSelected) = remember {
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
    val (teamsColorsSelectedOption, teamsColorsOnOptionSelected) = remember {
        mutableStateOf(
            teamsColors[teamsColor]
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
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
                    FilterChip(
                        selected = (gameMods[i] == modeSelectedOption),
                        onClick = { modeOnOptionSelected(gameMods[i]) },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = colorResource(
                                id = R.color.dark_gray
                            ),
                            selectedContainerColor = colorResource(id = R.color.dark_gray)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = colorResource(id = R.color.white),
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
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
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
                for (i in gameType.indices) {
                    FilterChip(
                        selected = (gameType[i] == typeSelectedOption),
                        onClick = { typeOnOptionSelected(gameType[i]) },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .weight(1F)
                            .padding(end = 8.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = colorResource(
                                id = R.color.dark_gray
                            ),
                            selectedContainerColor = colorResource(id = R.color.dark_gray)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = colorResource(id = R.color.white),
                            selectedBorderWidth = 1.dp
                        ),
                        label = {
                            Text(
                                text = gameType[i],
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
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
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
                            containerColor = colorResource(
                                id = R.color.dark_gray
                            ),
                            selectedContainerColor = colorResource(id = R.color.dark_gray)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = colorResource(id = R.color.white),
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
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
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
                            containerColor = colorResource(
                                id = R.color.dark_gray
                            ),
                            selectedContainerColor = colorResource(id = R.color.dark_gray)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selectedBorderColor = colorResource(id = R.color.white),
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
        Card(
            modifier = cardModifier,
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
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
                                Color.White
                            ) else BorderStroke(1.dp, Color.Transparent),
                            RoundedCornerShape(6.dp)
                        )

                    )
                    {
                        Text(
                            text = text,
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
                                Color.White
                            ) else BorderStroke(1.dp, Color.Transparent),
                            RoundedCornerShape(6.dp)
                        )
                    )
                    {
                        Text(
                            text = text,
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
        ) {
            Button(
                onClick = {
                    mViewModel.putInt("game_mode", when(modeSelectedOption) {
                        "Offline" -> 0
                        else -> 1
                    })
                    mViewModel.putInt("game_type", when(typeSelectedOption) {
                        "Words" -> 0
                        else -> 1
                    })
                    mViewModel.putInt("size", sizeSelectedOption.toInt())
                    mViewModel.putInt("complexity", when(complexitySelectedOption) {
                        "\uD83E\uDD0D" -> 0
                        "\uD83E\uDD0D \uD83E\uDD0D" -> 1
                        else -> 2
                    })
                    mViewModel.putInt("teams_color", when (teamsColorsSelectedOption) {
                        "Wild berries" -> 0
                        "Mustard field" -> 1
                        "Carrot freshness" -> 2
                        "Noble Saffron" -> 3
                        "Lilac at midnight" -> 4
                        else -> 5
                    })
                    navController.navigate("leader")
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.light_gray))
            ) {
                Text(
                    text = stringResource(id = R.string.fragment_create_game_create),
                    style = MainText
                )
            }
        }
    }
}

