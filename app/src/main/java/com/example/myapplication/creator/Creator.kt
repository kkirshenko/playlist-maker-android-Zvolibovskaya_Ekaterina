package com.example.myapplication.creator

import com.example.myapplication.data.network.RetrofitNetworkClient
import com.example.myapplication.data.network.TracksRepositoryImpl
import com.example.myapplication.domain.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(Storage()))
    }
}