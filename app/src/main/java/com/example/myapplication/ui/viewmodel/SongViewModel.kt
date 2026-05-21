package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.api.PlaylistsRepository
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SongViewModel (private val tracksRepository: TracksRepository, private val playlistsRepository: PlaylistsRepository): ViewModel() {

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { list ->
            emit(list)
        }
    }

    fun toggleFavorite(trackId: Long, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(trackId, isFavorite)
        }
    }
    fun getTrackById (trackId: Long) : Flow<Track?> {
        return tracksRepository.getTrackByID(trackId)
    }

    fun insertSongToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.insertSongToPlaylist(track, playlistId)
        }
    }

}