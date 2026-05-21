package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.api.ThemeRepository
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(private val tracksRepository: TracksRepository,  themeRepository: ThemeRepository): ViewModel() {

    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    val isDarkTheme = themeRepository.getTheme()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleFavorite(trackId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(trackId, isFavorite)
        }
    }
}