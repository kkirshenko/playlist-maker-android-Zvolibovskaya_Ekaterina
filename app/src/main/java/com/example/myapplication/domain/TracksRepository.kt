package com.example.myapplication.domain

import com.example.myapplication.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}