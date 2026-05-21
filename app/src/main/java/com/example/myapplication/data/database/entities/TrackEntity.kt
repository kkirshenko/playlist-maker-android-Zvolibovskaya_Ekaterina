package com.example.myapplication.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val image: String?,
    val favorite: Boolean = false,
    val playlistId: Long
)
