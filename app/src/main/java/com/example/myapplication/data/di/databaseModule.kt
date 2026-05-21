package com.example.myapplication.data.di

import androidx.room.Room
import com.example.myapplication.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single<AppDatabase> {

        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database.db"
        )
            .build()
    }

    single { get<AppDatabase>().trackDao() }
    single { get<AppDatabase>().playlistDao() }
}
