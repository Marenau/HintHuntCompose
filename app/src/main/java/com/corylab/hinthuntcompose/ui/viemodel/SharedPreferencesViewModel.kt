package com.corylab.hinthuntcompose.ui.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corylab.hinthuntcompose.data.repository.Repository

class SharedPreferencesViewModel(private val repository: Repository) : ViewModel() {

    fun getInt(key: String) = repository.getInt(key)

    fun putInt(key: String, number: Int) = repository.putInt(key, number)

    fun getString(key: String) = repository.getString(key)

    fun putString(key: String, text: String) = repository.putString(key, text)

    class SharedPreferencesModelFactory(
        private val context: Context,
        private val repository: Repository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SharedPreferencesViewModel(repository) as T
    }
}