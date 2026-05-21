package com.example.myapplication.data.repositories


import com.example.myapplication.data.database.DatabaseMock
import com.example.myapplication.data.dto.TracksSearchRequest
import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.NetworkClient
import com.example.myapplication.domain.models.Track
import com.example.myapplication.domain.repositories.TracksRepository
import kotlinx.coroutines.flow.Flow


class TracksRepositoryImpl(
    private val database: DatabaseMock,
    private val networkClient: NetworkClient
) : TracksRepository {


    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getTrackByID(trackId : Long): Flow<Track?> {
        return database.getTrackByID(trackId)
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

        val dto = TracksSearchRequest(expression)
        val response = networkClient.doRequest(dto)

        if (response.resultCode != 200 || response !is TracksSearchResponse) {
            return emptyList()
        }

        val savedTracks =  database.addTracks(response)

        return savedTracks
    }

}