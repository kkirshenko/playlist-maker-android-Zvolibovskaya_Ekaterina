package com.example.myapplication.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.data.preferences.SearchHistoryPreferences
import com.example.myapplication.data.preferences.ThemePreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "search_history")
private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_prefs")
val preferencesModule = module {
    single { SearchHistoryPreferences( androidContext().dataStore, get()) }
    single { ThemePreferences(androidContext().themeDataStore) }

}