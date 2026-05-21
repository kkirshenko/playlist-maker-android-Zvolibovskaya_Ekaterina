package com.example.myapplication.data.di

import com.example.myapplication.data.database.DatabaseMock
import com.example.myapplication.data.repositories.PlaylistsRepositoryImpl
import com.example.myapplication.data.repositories.SearchHistoryRepositoryImpl
import com.example.myapplication.data.repositories.TracksRepositoryImpl
import com.example.myapplication.domain.repositories.PlaylistsRepository
import com.example.myapplication.domain.repositories.SearchHistoryRepository
import com.example.myapplication.domain.repositories.TracksRepository
import org.koin.dsl.module


val repositoryModule = module {
    single{ DatabaseMock() }
    single<TracksRepository> { TracksRepositoryImpl(database = get()) }
    single<PlaylistsRepository> { PlaylistsRepositoryImpl(database = get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(database = get()) }
}