package com.corylab.hinthunt.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.corylab.hinthunt.R
import com.corylab.hinthunt.ui.theme.MainText
import com.corylab.hinthunt.ui.theme.Title

@Composable
fun Authors(navController: NavController) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.fragment_authors_title),
            style = Title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontSize = 40.sp
        )
        Column(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val projectManager =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_project_manager))
                    .append("\n").append(stringResource(id = R.string.fragment_authors_vadim_malin))
                    .toString()
            Text(
                text = projectManager,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val mainDeveloper =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_main_developer))
                    .append("\n").append(stringResource(id = R.string.fragment_authors_vadim_malin))
                    .toString()
            Text(
                text = mainDeveloper,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val developer =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_developer))
                    .append("\n").append(stringResource(id = R.string.fragment_authors_vadim_malin))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_svetlana_zharkova))
                    .toString()
            Text(
                text = developer,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val designer =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_designer))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_arina_mustafayeva))
                    .toString()
            Text(
                text = designer,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val technicalWriter =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_technical_writer))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_lina_barsh))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_arina_mustafayeva))
                    .toString()
            Text(
                text = technicalWriter,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val systemAnalyst =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_system_analyst))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_sofia_tretyakova))
                    .toString()
            Text(
                text = systemAnalyst,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val businessAnalyst =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_business_analyst))
                    .append("\n").append(stringResource(id = R.string.fragment_authors_lina_barsh))
                    .toString()
            Text(
                text = businessAnalyst,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            val thanks =
                StringBuilder().append(stringResource(id = R.string.fragment_authors_thanks))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_maria_dombrovskaya))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_ivan_dobrokhvalov))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_maksim_petrunin))
                    .append("\n")
                    .append(stringResource(id = R.string.fragment_authors_nikita_novichkov))
                    .toString()
            Text(
                text = thanks,
                style = MainText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
        Box(
            modifier = Modifier.padding(top = 24.dp)
        ) {
            ButtonWithText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.fragment_authors_support_developers),
                onClick = {}
            )
        }
    }
}