package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.api.SearchHistoryRepository
import com.example.myapplication.domain.api.ThemeRepository
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(FlowPreview::class)
class SearchViewModel(private val tracksRepository: TracksRepository,private val searchHistoryRepository: SearchHistoryRepository, themeRepository: ThemeRepository) : ViewModel() {


    private val _searchQuery = MutableStateFlow("")
    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    val isDarkTheme = themeRepository.getTheme()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)


    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(1000)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateQuery(query: String, immediate: Boolean = false) {
        _searchQuery.value = query
        if (immediate && query.isNotEmpty()) {
            performSearch(query)
        }
    }

    private fun performSearch(request: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                searchHistoryRepository.addToHistory(request)
                val list = tracksRepository.searchTracks(expression = request)
                _searchScreenState.update { SearchState.Success(list) }
            } catch (e: IOException) {
                _searchScreenState.update { SearchState.Fail(e.message.toString()) }
            }
        }
    }

    fun clearSearch() {
        _searchScreenState.update { SearchState.Initial }
    }

     fun getHistoryList() = searchHistoryRepository.getHistoryRequests()



}