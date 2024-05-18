package com.corylab.hinthunt.ui.screens

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.corylab.hinthunt.R
import com.corylab.hinthunt.ui.theme.MainText
import com.corylab.hinthunt.ui.theme.Title
import com.corylab.hinthunt.ui.viemodel.SharedPreferencesViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(mViewModel: SharedPreferencesViewModel) {
    val scrollState = rememberScrollState()

    val language = mViewModel.getInt("language")
    val theme = mViewModel.getInt("theme")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.fragment_settings_title),
            style = Title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            fontSize = 40.sp
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.fragment_settings_color_scheme),
                    style = MainText,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    fontSize = 18.sp
                )

                val schemes = listOf(
                    stringResource(id = R.string.fragment_settings_color_scheme_system_chip),
                    stringResource(id = R.string.fragment_settings_color_scheme_light_chip),
                    stringResource(id = R.string.fragment_settings_color_scheme_dark_chip)
                )
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(schemes[theme]) }
                Row(
                    Modifier
                        .selectableGroup()
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    schemes.forEach { text ->
                        FilterChip(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                                setTheme(
                                    when (text) {
                                        schemes[0] -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                                        schemes[1] -> AppCompatDelegate.MODE_NIGHT_NO
                                        schemes[2] -> AppCompatDelegate.MODE_NIGHT_YES
                                        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                                    }
                                )
                                mViewModel.putInt(
                                    "theme",
                                    when (text) {
                                        schemes[0] -> 0
                                        schemes[1] -> 1
                                        else -> 2
                                    }
                                )
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.fragment_settings_language),
                    style = MainText,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    fontSize = 18.sp
                )

                val languages = listOf("en", "ru")
                val context = LocalContext.current
                val (selectedLanguage, onLanguageSelected) = remember { mutableStateOf(languages[language]) }

                Row(
                    Modifier
                        .selectableGroup()
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    listOf("ru", "en").forEach { languageCode ->
                        FilterChip(
                            selected = (languageCode == selectedLanguage),
                            onClick = {
                                onLanguageSelected(languageCode)
                                mViewModel.putInt("language", if (languageCode == "en") 0 else 1)
                                localeSelection(
                                    context = context,
                                    localeTag = Locale(languageCode).toLanguageTag()
                                )
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
                                    text = stringResource(
                                        id = if (languageCode == "ru") R.string.fragment_settings_language_russian else R.string.fragment_settings_language_english
                                    ),
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
    }
}

fun setTheme(theme: Int) {
    AppCompatDelegate.setDefaultNightMode(theme)
}

fun localeSelection(context: Context, localeTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(localeTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(localeTag)
        )
    }
}

