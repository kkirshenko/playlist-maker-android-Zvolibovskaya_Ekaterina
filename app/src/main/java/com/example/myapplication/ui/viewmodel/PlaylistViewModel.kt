package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope

import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.repositories.PlaylistsRepository
import com.example.myapplication.domain.repositories.TracksRepository
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

    fun getPlaylistById(playlistId: Long) : Flow<Playlist?> {
        return playlistsRepository.getPlaylist(playlistId)
    }


    // val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
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

     fun toggleFavorite(trackId: Long, isFavorite: Boolean) {
         viewModelScope.launch(Dispatchers.IO) {
             tracksRepository.updateTrackFavoriteStatus(trackId, isFavorite)
         }
    }

    suspend fun deleteSongFromPlaylist(track: Track) {
        tracksRepository.deleteSongFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id = id)
    }

    fun isExist(track: Track): Flow<Track?> {
        return tracksRepository.getTrackByNameAndArtist(track = track)
    }

    fun getTracksFromPlaylistById(playlistId: Long): Flow<List<Track>>{
        return tracksRepository.getTracksByPlaylist(playlistId)
    }

    fun getCountTrackFromPlaylist(playlistId: Long): Flow<Int> {
        return tracksRepository.getCountTrackFromPlaylist(playlistId)
    }

}