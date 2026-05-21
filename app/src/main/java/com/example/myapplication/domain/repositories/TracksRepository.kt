package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>
    suspend fun getFavoriteTracks(): Flow<List<Track>>
    fun getTrackByID(trackId : Long): Track?
    suspend fun insertSongToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteSongFromPlaylist(track: Track)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    suspend fun deleteTracksByPlaylistId(playlistId: Long)
    suspend fun searchTracks(expression: String): List<Track>
}