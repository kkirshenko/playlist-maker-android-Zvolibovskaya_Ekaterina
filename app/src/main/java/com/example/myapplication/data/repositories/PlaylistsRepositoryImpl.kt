package com.example.myapplication.data.repositories


import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.database.entities.PlaylistEntity
import com.example.myapplication.data.mappers.toDomain
import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.repositories.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase
) : PlaylistsRepository {

    private val dao = database.playlistDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return dao.getPlaylist(playlistId).map { entity ->
            entity?.toDomain()
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return dao.getAllPlaylists().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        dao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
            )
        )
    }
    override suspend fun deletePlaylistById(id: Long) {
        dao.deletePlaylist(id)
    }
}

