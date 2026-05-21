package com.example.myapplication.data.mappers

import com.example.myapplication.data.database.entities.TrackEntity
import com.example.myapplication.data.dto.TrackDto
import com.example.myapplication.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

fun TrackEntity.toDomain() = Track(
    id = id,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    image = image,
    favorite = favorite,
    playlistId = playlistId
)

fun Track.toEntity() = TrackEntity(
    id = id,
    trackName = trackName,
    artistName = artistName,
    trackTime = trackTime,
    image = image,
    favorite = favorite,
    playlistId = playlistId
)

fun TrackDto.toEntity() = TrackEntity(
    id = trackId,
    trackName = trackName,
    artistName = artistName,
    trackTime = SimpleDateFormat("mm:ss", Locale.getDefault())
        .format(trackTimeMillis),
    image = artworkUrl100,
    favorite = false,
    playlistId = 0
)

fun TrackDto.toDomain() = Track(
    id = trackId,
    trackName = trackName,
    artistName = artistName,
    trackTime = SimpleDateFormat("mm:ss", Locale.getDefault())
        .format(trackTimeMillis),
    image = artworkUrl100,
    favorite = false,
    playlistId = 0
)

