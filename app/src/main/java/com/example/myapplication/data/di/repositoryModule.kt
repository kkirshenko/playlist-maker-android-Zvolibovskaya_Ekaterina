package com.example.myapplication.data.di

import com.example.myapplication.data.network.ITunesApiService
import com.example.myapplication.data.network.RetrofitNetworkClient
import com.example.myapplication.data.repositories.PlaylistsRepositoryImpl
import com.example.myapplication.data.repositories.SearchHistoryRepositoryImpl
import com.example.myapplication.data.repositories.TracksRepositoryImpl
import com.example.myapplication.domain.NetworkClient
import com.example.myapplication.domain.repositories.PlaylistsRepository
import com.example.myapplication.domain.repositories.SearchHistoryRepository
import com.example.myapplication.domain.repositories.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val repositoryModule = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ITunesApiService> { get<Retrofit>().create(ITunesApiService::class.java) }
    single<NetworkClient> { RetrofitNetworkClient(api = get()) }
    single<TracksRepository> { TracksRepositoryImpl(get(), get()) }
    single<PlaylistsRepository> { PlaylistsRepositoryImpl( get()) }
    single<SearchHistoryRepository> { SearchHistoryRepositoryImpl(get()) }
}