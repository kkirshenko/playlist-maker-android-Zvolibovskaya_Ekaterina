package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    fun getFavoriteTracks(): Flow<List<Track>>
    fun getTrackByID(trackId : Long): Flow<Track?>
    suspend fun insertSongToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteSongFromPlaylist(track: Track)
    suspend fun updateTrackFavoriteStatus(trackId: Long, isFavorite: Boolean)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
    suspend fun searchTracks(expression: String): List<Track>
    fun getTracksByPlaylist(playlistId: Long): Flow<List<Track>>
    fun getCountTrackFromPlaylist(playlistId: Long): Flow<Int>
}