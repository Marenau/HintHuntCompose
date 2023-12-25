package com.corylab.hinthuntcompose.ui.viemodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.corylab.hinthuntcompose.data.repository.Repository

class SharedPreferencesViewModel(application: Application) : ViewModel() {
    private val wordRepository: Repository = Repository(application)

    fun getInt(key: String) = wordRepository.getInt(key)

    fun putInt(key: String, number: Int) = wordRepository.putInt(key, number)

    fun getString(key: String) = wordRepository.getString(key)

    fun putString(key: String, text: String) = wordRepository.putString(key, text)
}