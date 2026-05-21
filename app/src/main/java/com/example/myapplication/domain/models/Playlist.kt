package com.example.myapplication.domain.models


data class Playlist(
    val id: Long,
    val name: String,
    val description: String,
    val coverImageUri: String? = null
)