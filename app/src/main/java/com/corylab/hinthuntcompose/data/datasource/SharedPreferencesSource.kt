package com.corylab.hinthuntcompose.data.datasource

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesSource(context: Context) {
    private val PREF_NAME = "appPrefs"
    private var _sharedPreferences: SharedPreferences? = null
    private val sharedPreferences get() = _sharedPreferences!!
    private var editor: SharedPreferences.Editor

    init {
        _sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun putString(key: String, value: String) = editor.putString(key, value).commit()

    fun getString(key: String, defaultValue: String = "empty") = sharedPreferences.getString(key, defaultValue)

    fun putInt(key: String, value: Int) = editor.putInt(key, value).commit()

    fun getInt(key: String, defaultValue: Int = 0) = sharedPreferences.getInt(key, defaultValue)
}