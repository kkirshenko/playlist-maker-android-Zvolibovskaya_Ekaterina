package com.example.myapplication.domain.api

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    fun getTheme(): Flow<Boolean>
    suspend fun setTheme(isDark: Boolean)
}