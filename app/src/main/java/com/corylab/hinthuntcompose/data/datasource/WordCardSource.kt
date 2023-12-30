package com.corylab.hinthuntcompose.data.datasource

import com.corylab.hinthuntcompose.ui.application.HintHunt
import com.corylab.hinthuntcompose.R
import java.util.Random

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

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int): Array<Int> {
        val colorsNum = Array(size) { 0 }
        generateNum(colorsNum, 1, firstNumOfCard)
        generateNum(colorsNum, 2, secondNumOfCard)
        generateNum(colorsNum, 3, 1)
        return colorsNum
    }

    private fun generateNum(arr: Array<Int>, num: Int, size: Int) {
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