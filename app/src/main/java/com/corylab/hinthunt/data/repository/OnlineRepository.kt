package com.corylab.hinthunt.data.repository

import android.content.Context
import com.corylab.hinthunt.data.datasource.FirebaseSource
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.ValueEventListener

class OnlineRepository {
    private val firebaseSource = FirebaseSource()

    fun initiateKey(key: String) = firebaseSource.initiateKey(key)

    fun putKey(key: String) = firebaseSource.putKey(key)

    fun putWords(words: List<String>) = firebaseSource.putWords(words)

    fun putNumOfCards(command: Int, num: Int) = firebaseSource.putNumOfCards(command, num)

    fun putScore(command: Int, score: Int) = firebaseSource.putScore(command, score)

    fun putTurn(turn: Int) = firebaseSource.putTurn(turn)

    fun putColorNums(colors: List<Int>) = firebaseSource.putColorNums(colors)

    fun putTeamsColors(numOfColors: Int) = firebaseSource.putTeamsColors(numOfColors)

    fun putSelectedColor(selectedColors: List<Boolean>) = firebaseSource.putSelectedColor(selectedColors)

    fun putWinner(winner: Int) = firebaseSource.putWinner(winner)

    fun putComplexity(complexity: Int) = firebaseSource.putComplexity(complexity)

    fun putLang(lang: Int) = firebaseSource.putLang(lang)

    fun putSize(size: Int) = firebaseSource.putSize(size)

    fun putSelectedColor(index: Int, state: Boolean) = firebaseSource.putSelectedColor(index, state)

    fun getWords() = firebaseSource.getWords()

    fun getNumOfCards(command: Int) = firebaseSource.getNumOfCards(command)

    fun getScore(command: Int) = firebaseSource.getScore(command)

    fun getTurn() = firebaseSource.getTurn()

    fun getColorNums() = firebaseSource.getColorNums()

    fun getTeamsColors() = firebaseSource.getTeamsColors()

    fun getWinner() = firebaseSource.getWinner()

    fun getSelectedColors() = firebaseSource.getSelectedColors()

    fun getComplexity() = firebaseSource.getComplexity()

    fun getLang() = firebaseSource.getLang()

    fun getSize() = firebaseSource.getSize()
}