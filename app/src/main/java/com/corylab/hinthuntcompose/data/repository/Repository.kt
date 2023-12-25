package com.corylab.hinthuntcompose.data.repository

import android.content.Context
import com.corylab.hinthuntcompose.data.datasource.SharedPreferencesSource
import com.corylab.hinthuntcompose.data.datasource.WordCardSource

class Repository(application: Context) {
    private val sharedPreferencesSource = SharedPreferencesSource(application)
    private val wordCardSource = WordCardSource()

    fun getInt(key: String) = sharedPreferencesSource.getInt(key)

    fun putInt(key: String, number: Int) = sharedPreferencesSource.putInt(key, number)

    fun getString(key: String) = sharedPreferencesSource.getString(key)

    fun putString(key: String, text: String) = sharedPreferencesSource.putString(key, text)

    private fun getSize() = getInt("size")

    private fun getEasyWords() = wordCardSource.getEasyWords(getSize())

    private fun getMediumWords() = wordCardSource.getMediumWords(getSize())

    private fun getHardWords() = wordCardSource.getHardWords(getSize())

    fun getWords(): List<String> {
        val words: List<String> = when (sharedPreferencesSource.getInt("complexity")) {
            1 -> getEasyWords()
            2 -> getMediumWords()
            else -> getHardWords()
        }
        return words
    }
}