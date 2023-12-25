package com.corylab.hinthuntcompose.data.datasource

import com.corylab.hinthuntcompose.ui.application.HintHunt
import com.corylab.hinthuntcompose.R

class WordCardSource {
    fun getEasyWords(size: Int) =
        HintHunt.context.resources.openRawResource(R.raw.easy_words).bufferedReader().readLines()
            .shuffled().take(size)

    fun getMediumWords(size: Int) =
        HintHunt.context.resources.openRawResource(R.raw.medium_words).bufferedReader().readLines()
            .shuffled().take(size)

    fun getHardWords(size: Int) =
        HintHunt.context.resources.openRawResource(R.raw.hard_words).bufferedReader().readLines()
            .shuffled().take(size)
}