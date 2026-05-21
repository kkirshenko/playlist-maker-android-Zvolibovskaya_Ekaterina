package com.example.myapplication.data.repositories


import com.example.myapplication.data.database.DatabaseMock
import com.example.myapplication.domain.repositories.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow


class SearchHistoryRepositoryImpl( private val database: DatabaseMock): SearchHistoryRepository {


    override  fun getHistoryRequests(): Flow<List<String>> {
        return database.getHistoryRequests()
    }

    override fun addToHistory(word: String) {
        database.addToHistory(word = word)
    }
}