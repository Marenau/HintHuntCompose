package com.corylab.hinthunt.ui.viemodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.corylab.hinthunt.data.repository.OfflineRepository

class SharedPreferencesViewModel(private val offlineRepository: OfflineRepository) : ViewModel() {

    fun getInt(key: String) = offlineRepository.getInt(key)

    fun putInt(key: String, number: Int) = offlineRepository.putInt(key, number)

    fun getString(key: String) = offlineRepository.getString(key)

    fun putString(key: String, text: String) = offlineRepository.putString(key, text)

    fun getBoolean(key: String) = offlineRepository.getBoolean(key)

    fun putBoolean(key: String, bool: Boolean) = offlineRepository.putBoolean(key, bool)

    class SharedPreferencesModelFactory(
        private val context: Context,
        private val offlineRepository: OfflineRepository
    ) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SharedPreferencesViewModel(offlineRepository) as T
    }
}