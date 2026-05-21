package com.example.myapplication.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences

private val THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

class ThemePreferences(private val dataStore: DataStore<Preferences>) {

    suspend fun setTheme(isDark: Boolean) {
        dataStore.edit { prefs ->
            prefs[THEME_KEY] = isDark
        }
    }

    fun getTheme(): Flow<Boolean> =
        dataStore.data.map { prefs ->
            prefs[THEME_KEY] ?: false
        }
}