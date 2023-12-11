package com.corylab.hinthuntcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corylab.hinthuntcompose.R
import com.corylab.hinthuntcompose.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Leader() {
    //TODO
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val context = LocalContext.current
        val k = remember {
            context.resources.openRawResource(R.raw.easy_words).bufferedReader().readLines()
                .shuffled().take(3)
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            for (i in k) {
                var textColor by remember { mutableStateOf(Color.White) }
                Button(
                    onClick = {
                        textColor = if (textColor == Color.White) Color.Black else Color.White
                    }
                ) {
                    Text(
                        text = i,
                        color = textColor,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            var selected1 by remember { mutableStateOf(false) }
            var selected2 by remember { mutableStateOf(false) }
            FilterChip(
                selected = selected1,
                onClick = {
                    selected1 = !selected1
                    selected2 = false
                },
                label = { Text(text = "Piska1") })
            FilterChip(
                selected = selected2,
                onClick = {
                    selected2 = !selected2
                    selected1 = false
                },
                label = { Text(text = "Piska2") })
        }
    }
}