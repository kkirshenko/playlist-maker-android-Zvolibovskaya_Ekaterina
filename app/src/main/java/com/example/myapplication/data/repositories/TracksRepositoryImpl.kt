package com.example.myapplication.data.repositories


import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.data.mappers.toDomain
import com.example.myapplication.data.mappers.toEntity
import com.example.myapplication.data.network.NetworkClient
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.api.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import kotlin.collections.map


class TracksRepositoryImpl(
    database: AppDatabase,
    private val networkClient: NetworkClient
) : TracksRepository {

    private val dao = database.trackDao()

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return dao.getTrackByNameAndArtist(track.trackName, track.artistName).map { entity ->
            entity?.toDomain()
        }
    }

    override fun getTrackByID(trackId : Long): Flow<Track?> {
        return dao.getTrack(trackId).map { entity ->
            entity?.toDomain()
        }
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        dao.insertTrackToPlaylist(track.id, playlistId)
    }

    override suspend fun deleteSongFromPlaylist(track: Track) {
        dao.deleteTrackFromPlaylist(track.id)
    }

    override suspend fun updateTrackFavoriteStatus(trackId: Long, isFavorite: Boolean) {
        dao.updateFavoriteStatus(trackId, isFavorite)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getFavoriteTracks().map { list ->
            list.map { it.toDomain() }
        }
    }
    override fun getTracksByPlaylist(playlistId: Long): Flow<List<Track>> {
        return dao.getTracksByPlaylist(playlistId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        dao.deletePlaylistById(playlistId)
    }
    override suspend fun searchTracks(expression: String): List<Track> {

        val dto = TracksSearchRequest(expression)
        val response = networkClient.doRequest(dto)

        if (response.resultCode != 200 || response !is TracksSearchResponse) {
            throw IOException("Ошибка сети: ${response.errorMessage}")
        }

        response.results.map { track -> dao.insertTrack(track.toEntity()) }

        val savedTrack = response.results.map { track -> track.toDomain()}

        return savedTrack
    }
    override fun getCountTrackFromPlaylist(playlistId: Long): Flow<Int>{
        return dao.getCountTrackFromPlaylist(playlistId)
    }

    override fun getCountMinutesFromPlaylist(playlistId: Long): Flow<Int> = flow {
        val timesOfTracks = dao.getMinuteFromPlaylist(playlistId)
        val totalMinutes = timesOfTracks.sumOf { trackTime ->
            val parts = trackTime.split(":")
            val minutes = parts[0].toInt()
            val seconds = parts[1].toInt()
            minutes + if (seconds >= 30) 1 else 0
        }
        emit(totalMinutes)
    }


}