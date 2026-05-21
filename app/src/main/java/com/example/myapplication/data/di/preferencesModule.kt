package com.example.myapplication.data.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.data.preferences.SearchHistoryPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "search_history")

val preferencesModule = module {
    single { SearchHistoryPreferences( androidContext().dataStore, get()) }
}