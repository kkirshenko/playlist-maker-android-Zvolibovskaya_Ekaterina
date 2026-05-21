package com.example.myapplication.data.repositories


import com.example.myapplication.data.database.DatabaseMock
import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.repositories.PlaylistsRepository
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock
) : PlaylistsRepository {


    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(name, description)
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(id = id)
    }
}
