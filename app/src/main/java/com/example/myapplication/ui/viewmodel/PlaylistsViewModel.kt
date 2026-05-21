package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.api.PlaylistsRepository
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsViewModel(private val playlistsRepository: PlaylistsRepository, private val tracksRepository: TracksRepository ): ViewModel(){

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { list ->
            emit(list)
        }
    }

    fun getCountTrackFromPlaylist(playlistId: Long): Flow<Int> {
        return tracksRepository.getCountTrackFromPlaylist(playlistId)
    }

}