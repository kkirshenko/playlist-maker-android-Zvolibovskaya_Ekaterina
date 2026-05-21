package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.database.dao.PlaylistDao
import com.example.myapplication.data.database.dao.TrackDao
import com.example.myapplication.data.database.entities.PlaylistEntity
import com.example.myapplication.data.database.entities.TrackEntity

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao

}
