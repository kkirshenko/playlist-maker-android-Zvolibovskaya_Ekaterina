package com.example.myapplication.data.repositories

import android.util.Log
import com.example.myapplication.data.database.DatabaseMock
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.repositories.TracksRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


class TracksRepositoryImpl(
    private val database: DatabaseMock) : TracksRepository {


    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getTrackByID(trackId : Long): Track? {
        Log.i("abc3", trackId.toString())
        return listTracks.find { it.id == trackId }
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteSongFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deletePlaylistById(playlistId)
    }
    override suspend fun searchTracks(expression: String): List<Track> {
        delay(1000)
        return listTracks.filter { it.trackName.lowercase().contains(expression.lowercase()) }
    }

    val listTracks = listOf(
        Track(
            id = 1,
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTime = "2:38",
            image = "",
            favorite = false,
            playlistId = 0
        ),

        Track(
            id = 10,
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTime = "4:01",
            image = "",
            favorite = false,
            playlistId = 0
        )
    )
}