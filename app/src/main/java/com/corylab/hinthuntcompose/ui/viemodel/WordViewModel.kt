package com.corylab.hinthuntcompose.ui.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corylab.hinthuntcompose.data.repository.Repository

class WordViewModel(private val repository: Repository) : ViewModel() {

    fun getWords() = repository.getWords()

    class WordViewModelFactory(
        private val context: Context,
        private val repository: Repository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            WordViewModel(repository) as T
    }
}