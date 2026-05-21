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


class PlaylistViewModel(private val tracksRepository: TracksRepository, private val  playlistsRepository: PlaylistsRepository) : ViewModel() {


    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { list ->
            emit(list)
        }
    }

    fun getPlaylistById(playlistId: Long): Flow<Playlist?> {
        return playlistsRepository.getPlaylist(playlistId)
    }


    fun deleteSongFromPlaylist(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteSongFromPlaylist(track)
        }
    }

    fun getTracksFromPlaylistById(playlistId: Long): Flow<List<Track>> {
        return tracksRepository.getTracksByPlaylist(playlistId)
    }


    fun getCountTrackFromPlaylist(playlistId: Long): Flow<Int> {
        return tracksRepository.getCountTrackFromPlaylist(playlistId)
    }
    fun getMinutesFromPlaylist(playlistId: Long): Flow<Int>{
        return  tracksRepository.getCountMinutesFromPlaylist(playlistId)

    }
}