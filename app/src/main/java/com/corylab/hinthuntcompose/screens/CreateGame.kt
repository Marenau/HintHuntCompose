package com.corylab.hinthuntcompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.MainText
import com.corylab.hinthuntcompose.ui.theme.Title

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreateGame() {

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
            shape = RoundedCornerShape(6.dp)

        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_board_size),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            val sizes = listOf(
                stringResource(id = R.string.fragment_create_game_board_size_18),
                stringResource(id = R.string.fragment_create_game_board_size_24),
                stringResource(id = R.string.fragment_create_game_board_size_30)
            )
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(sizes[0]) }

            Row(
                Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                sizes.forEach { text, ->
                    FilterChip(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
            shape = RoundedCornerShape(6.dp)

        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_complexity_of_words,),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            val complexities = listOf(
                stringResource(id = R.string.fragment_create_game_words_complexity_easy),
                stringResource(id = R.string.fragment_create_game_words_complexity_medium),
                stringResource(id = R.string.fragment_create_game_words_complexity_hard)
            )
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(complexities[0]) }
            Row(
                Modifier
                    .selectableGroup()
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                complexities.forEach { text ->
                    FilterChip(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.light_gray)),
            shape = RoundedCornerShape(6.dp)

        ) {
            Text(
                text = stringResource(id = R.string.fragment_create_game_colors_of_teams),
                style = MainText,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
            )
            val teamsColors = listOf(
                stringResource(id = R.string.fragment_create_game_wild_berries),
                stringResource(id = R.string.fragment_create_game_mustard_field),
                stringResource(id = R.string.fragment_create_game_carrot_freshness),
                stringResource(id = R.string.fragment_create_game_noble_saffron),
                stringResource(id = R.string.fragment_create_game_lilac_at_midnight),
                stringResource(id = R.string.fragment_create_game_cranberries_in_moss)
            )
            val brushes = listOf(
                Brush.horizontalGradient(listOf(colorResource(id = R.color.wild_berries_color1), colorResource(R.color.wild_berries_color2))),
                Brush.horizontalGradient(listOf(colorResource(id = R.color.mustard_field_color1), colorResource(R.color.mustard_field_color2))),
                Brush.horizontalGradient(listOf(colorResource(id = R.color.carrot_freshness_color1), colorResource(R.color.carrot_freshness_color2))),
                Brush.horizontalGradient(listOf(colorResource(id = R.color.noble_saffron_color1), colorResource(R.color.noble_saffron_color2))),
                Brush.horizontalGradient(listOf(colorResource(id = R.color.lilac_at_midnight_color1), colorResource(R.color.lilac_at_midnight_color2))),
                Brush.horizontalGradient(listOf(colorResource(id = R.color.cranberries_in_moss_color1), colorResource(R.color.cranberries_in_moss_color2)))
            )


            var selectedOption by remember {
                mutableStateOf(teamsColors[0])
            }
            val onSelectionChange = { text: String ->
                selectedOption = text
            }

            Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    var index = 0
                    teamsColors.take(3).forEach { text ->
                            Box (modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .clip(shape = RoundedCornerShape(6.dp))
                                .clickable {
                                    onSelectionChange(text)
                                }
                                .background(
                                    brush = brushes[index],
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    if (text == selectedOption) BorderStroke(1.dp, Color.White) else BorderStroke(1.dp, Color.Transparent),RoundedCornerShape(6.dp))

                            )
                            {
                                Text(text = text, style = MainText, textAlign = TextAlign.Center, modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 8.dp)
                                    .fillMaxWidth(),
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap= false,
                                    fontSize = 12.sp)
                            }
                        index+=1
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
                            Box (modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp, bottom = 8.dp)
                                .clip(shape = RoundedCornerShape(6.dp))
                                .clickable {
                                    onSelectionChange(text)
                                }
                                .background(
                                    brush = brushes[index],
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .border(
                                    if (text == selectedOption) BorderStroke(1.dp, Color.White) else BorderStroke(1.dp, Color.Transparent),RoundedCornerShape(6.dp))
                            )
                                {
                                    Text(text = text, style = MainText, textAlign = TextAlign.Center, modifier = Modifier
                                        .padding(horizontal = 8.dp, vertical = 8.dp)
                                        .fillMaxWidth(),
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap= false,
                                        fontSize = 12.sp)
                                }
                            index+=1
                        }
                }


        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = { /* Handle create game click */ },
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

