package com.corylab.hinthunt.data.datasource

import com.corylab.hinthunt.ui.application.HintHunt
import com.corylab.hinthunt.R
import java.util.Random

class WordCardSource {
    fun getEasyWords(size: Int, lang: Int) = if (lang == 1)
        HintHunt.context.resources.openRawResource(R.raw.easy_words).bufferedReader().readLines()
            .shuffled()
            .take(size) else HintHunt.context.resources.openRawResource(R.raw.easy_words_en)
        .bufferedReader().readLines()
        .shuffled().take(size)

    fun getMediumWords(size: Int, lang: Int) = if (lang == 1)
        HintHunt.context.resources.openRawResource(R.raw.medium_words).bufferedReader().readLines()
            .shuffled()
            .take(size) else HintHunt.context.resources.openRawResource(R.raw.medium_words_en)
        .bufferedReader().readLines()
        .shuffled().take(size)

    fun getHardWords(size: Int, lang: Int) = if (lang == 1)
        HintHunt.context.resources.openRawResource(R.raw.hard_words).bufferedReader().readLines()
            .shuffled()
            .take(size) else HintHunt.context.resources.openRawResource(R.raw.hard_words_en)
        .bufferedReader().readLines()
        .shuffled().take(size)

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int): List<Int> {
        val colorsNum = MutableList(size) { 0 }
        generateNum(colorsNum, 1, firstNumOfCard)
        generateNum(colorsNum, 2, secondNumOfCard)
        generateNum(colorsNum, 3, 1)
        return colorsNum
    }

    private fun generateNum(arr: MutableList<Int>, num: Int, size: Int) {
        val rand = Random()
        for (i in 1..size) {
            var index: Int
            do {
                index = rand.nextInt(arr.size)
            } while (arr[index] != 0)
            arr[index] = num
        }
    }
}