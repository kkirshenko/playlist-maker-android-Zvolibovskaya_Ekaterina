package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.di.databaseModule
import com.example.myapplication.data.di.preferencesModule
import com.example.myapplication.data.di.repositoryModule
import com.example.myapplication.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(databaseModule,repositoryModule, preferencesModule, repositoryModule, viewModelModule))
        }

    }
}