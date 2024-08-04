package com.corylab.hinthunt.data.repository

import android.content.Context
import com.corylab.hinthunt.data.datasource.SharedPreferencesSource
import com.corylab.hinthunt.data.datasource.WordCardSource

class OfflineRepository(application: Context) {
    private val sharedPreferencesSource = SharedPreferencesSource(application)
    private val wordCardSource = WordCardSource()

    fun getInt(key: String) = sharedPreferencesSource.getInt(key)

    fun putInt(key: String, number: Int) = sharedPreferencesSource.putInt(key, number)

    fun getString(key: String) = sharedPreferencesSource.getString(key)

    fun putString(key: String, text: String) = sharedPreferencesSource.putString(key, text)

    fun getBoolean(key: String) = sharedPreferencesSource.getBoolean(key)

    fun putBoolean(key: String, bool: Boolean) = sharedPreferencesSource.putBoolean(key, bool)

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int) = wordCardSource.createColorsNums(size, firstNumOfCard, secondNumOfCard)

    private fun getSize() = getInt("size")

    private fun getLanguage() = getInt("language")

    private fun getComplexity() = getInt("complexity")

    private fun getRoomSize() = getInt("words_size")

    private fun getRoomLanguage() = getInt("words_language")

    private fun getRoomComplexity() = getInt("words_complexity")

    private fun getEasyWords() = wordCardSource.getEasyWords(getSize(), getLanguage())

    private fun getMediumWords() = wordCardSource.getMediumWords(getSize(), getLanguage())

    private fun getHardWords() = wordCardSource.getHardWords(getSize(), getLanguage())

    private fun getNewEasyWords() = wordCardSource.getEasyWords(getRoomSize(), getRoomLanguage())

    private fun getNewMediumWords() = wordCardSource.getMediumWords(getRoomSize(), getRoomLanguage())

    private fun getNewHardWords() = wordCardSource.getHardWords(getRoomSize(), getRoomLanguage())

    fun getWords(): List<String> {
        val words: List<String> = when (getComplexity()) {
            0 -> getEasyWords()
            1 -> getMediumWords()
            else -> getHardWords()
        }
        return words
    }

    fun getNewWords(): List<String> {
        val words: List<String> = when (getRoomComplexity()) {
            0 -> getNewEasyWords()
            1 -> getNewMediumWords()
            else -> getNewHardWords()
        }
        return words
    }
}