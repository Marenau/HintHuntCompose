package com.corylab.hinthunt.ui.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corylab.hinthunt.data.repository.OfflineRepository

class WordViewModel(private val offlineRepository: OfflineRepository) : ViewModel() {

    fun getWords() = offlineRepository.getWords()

    fun getNewWords() = offlineRepository.getNewWords()

    fun createColorsNums(size: Int, firstNumOfCard: Int, secondNumOfCard: Int) = offlineRepository.createColorsNums(size, firstNumOfCard, secondNumOfCard)

    class WordViewModelFactory(
        private val context: Context,
        private val offlineRepository: OfflineRepository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            WordViewModel(offlineRepository) as T
    }
}