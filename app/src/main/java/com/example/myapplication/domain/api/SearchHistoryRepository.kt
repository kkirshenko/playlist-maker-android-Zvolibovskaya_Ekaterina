package com.example.myapplication.domain.api

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    fun getHistoryRequests(): Flow<List<String>>

    fun addToHistory(word: String)
}