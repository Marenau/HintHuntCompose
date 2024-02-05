package com.corylab.hinthunt.data.repository

import android.content.Context
import com.corylab.hinthunt.data.datasource.SharedPreferencesSource
import com.corylab.hinthunt.data.datasource.WordCardSource

class Repository(application: Context) {
    private val sharedPreferencesSource = SharedPreferencesSource(application)
    private val wordCardSource = WordCardSource()

    fun getInt(key: String) = sharedPreferencesSource.getInt(key)

    fun putInt(key: String, number: Int) = sharedPreferencesSource.putInt(key, number)

    fun getString(key: String) = sharedPreferencesSource.getString(key)

    fun putString(key: String, text: String) = sharedPreferencesSource.putString(key, text)

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int) = wordCardSource.createColorsNums(size, firstNumOfCard, secondNumOfCard)

    private fun getSize() = getInt("size")

    private fun getLanguage() = getInt("language")

    private fun getEasyWords() = wordCardSource.getEasyWords(getSize(), getLanguage())

    private fun getMediumWords() = wordCardSource.getMediumWords(getSize(), getLanguage())

    private fun getHardWords() = wordCardSource.getHardWords(getSize(), getLanguage())

    fun getWords(): List<String> {
        val words: List<String> = when (sharedPreferencesSource.getInt("complexity")) {
            0 -> getEasyWords()
            1 -> getMediumWords()
            else -> getHardWords()
        }
        return words
    }
}