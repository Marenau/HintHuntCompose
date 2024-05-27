package com.corylab.hinthunt.data.repository

import com.corylab.hinthunt.data.datasource.FirebaseWordSource

class OnlineRepository {
    private val firebaseWordSource = FirebaseWordSource()

    fun initiateKey(key: String) = firebaseWordSource.initiateKey(key)

    fun putKey(key: String) = firebaseWordSource.putKey(key)

    fun putWords(words: List<String>) = firebaseWordSource.putWords(words)

    fun putNumOfCards(command: Int, num: Int) = firebaseWordSource.putNumOfCards(command, num)

    fun putScore(command: Int, score: Int) = firebaseWordSource.putScore(command, score)

    fun putTurn(turn: Int) = firebaseWordSource.putTurn(turn)

    fun putColorNums(colors: List<Int>) = firebaseWordSource.putColorNums(colors)

    fun putTeamsColors(numOfColors: Int) = firebaseWordSource.putTeamsColors(numOfColors)

    fun putSelectedColor(selectedColors: List<Boolean>) = firebaseWordSource.putSelectedColor(selectedColors)

    fun putWinner(winner: Int) = firebaseWordSource.putWinner(winner)

    fun putComplexity(complexity: Int) = firebaseWordSource.putComplexity(complexity)

    fun putLang(lang: Int) = firebaseWordSource.putLang(lang)

    fun putSize(size: Int) = firebaseWordSource.putSize(size)

    fun putSelectedColor(index: Int, state: Boolean) = firebaseWordSource.putSelectedColor(index, state)

    fun getWords() = firebaseWordSource.getWords()

    fun getNumOfCards(command: Int) = firebaseWordSource.getNumOfCards(command)

    fun getScore(command: Int) = firebaseWordSource.getScore(command)

    fun getTurn() = firebaseWordSource.getTurn()

    fun getColorNums() = firebaseWordSource.getColorNums()

    fun getTeamsColors() = firebaseWordSource.getTeamsColors()

    fun getWinner() = firebaseWordSource.getWinner()

    fun getSelectedColors() = firebaseWordSource.getSelectedColors()

    fun getComplexity() = firebaseWordSource.getComplexity()

    fun getLang() = firebaseWordSource.getLang()

    fun getSize() = firebaseWordSource.getSize()
}