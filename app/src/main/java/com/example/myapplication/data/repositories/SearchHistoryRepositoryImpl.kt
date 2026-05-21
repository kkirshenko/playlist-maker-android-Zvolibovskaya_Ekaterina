package com.example.myapplication.data.repositories


import com.example.myapplication.data.preferences.SearchHistoryPreferences
import com.example.myapplication.domain.repositories.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow


class SearchHistoryRepositoryImpl( private val database: SearchHistoryPreferences): SearchHistoryRepository {


    override fun getHistoryRequests(): Flow<List<String>> {
        return database.getEntries()
    }

    override fun addToHistory(word: String) {
        database.addEntry(word = word)
    }
}