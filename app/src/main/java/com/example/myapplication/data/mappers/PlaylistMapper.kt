package com.example.myapplication.data.mappers

import com.example.myapplication.data.database.entities.PlaylistEntity
import com.example.myapplication.domain.models.Playlist

fun PlaylistEntity.toDomain() = Playlist(
    id = id,
    name = name,
    description = description,
    coverImageUri = coverImageUri
)

fun Playlist.toEntity() = PlaylistEntity(
    id = id,
    name = name,
    description = description,
    coverImageUri = coverImageUri
)
