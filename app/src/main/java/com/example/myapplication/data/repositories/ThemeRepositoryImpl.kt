package com.example.myapplication.data.repositories

import com.example.myapplication.data.preferences.ThemePreferences
import com.example.myapplication.domain.api.ThemeRepository
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val preferences: ThemePreferences
) : ThemeRepository {

    override fun getTheme(): Flow<Boolean> =
        preferences.getTheme()

    override suspend fun setTheme(isDark: Boolean) =
        preferences.setTheme(isDark)
}