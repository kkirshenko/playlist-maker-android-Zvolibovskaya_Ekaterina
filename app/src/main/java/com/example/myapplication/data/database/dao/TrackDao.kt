package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.data.database.entities.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks WHERE id = :id")
    fun getTrack(id: Long): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    fun getTrackByNameAndArtist(name: String, artist: String): Flow<TrackEntity?>

    @Query("SELECT * FROM tracks WHERE playlistId = :playlistId")
    fun getTracksByPlaylist(playlistId: Long): Flow<List<TrackEntity>>

    @Query("SELECT Count(*) FROM tracks WHERE playlistId = :id")
    fun getCountTrackFromPlaylist(id: Long): Flow<Int>

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Query("UPDATE tracks SET playlistId = :playlistId WHERE id = :trackId")
    suspend fun insertTrackToPlaylist(trackId : Long, playlistId: Long)

    @Query("UPDATE tracks SET playlistId = 0 WHERE id = :trackId")
    suspend fun deleteTrackFromPlaylist(trackId : Long)

    @Query("UPDATE tracks SET favorite = :favorite WHERE id = :trackId")
    suspend fun updateFavoriteStatus(trackId : Long, favorite: Boolean)

    @Query("UPDATE tracks SET playlistId = 0 WHERE playlistId = :playlistId")
    suspend fun deletePlaylistById(playlistId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity) : Long

    @Delete
    suspend fun deleteTrack(track: TrackEntity)
}
