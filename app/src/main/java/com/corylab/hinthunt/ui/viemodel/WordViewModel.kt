package com.corylab.hinthunt.ui.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corylab.hinthunt.data.repository.Repository

class WordViewModel(private val repository: Repository) : ViewModel() {

    fun getWords() = repository.getWords()

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int) = repository.createColorsNums(size, firstNumOfCard, secondNumOfCard)

    class WordViewModelFactory(
        private val context: Context,
        private val repository: Repository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            WordViewModel(repository) as T
    }
}