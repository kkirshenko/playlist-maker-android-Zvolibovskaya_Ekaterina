package com.example.myapplication.ui.di

import com.example.myapplication.ui.viewmodel.FavoriteViewModel
import com.example.myapplication.ui.viewmodel.NewPlaylistViewModel
import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import com.example.myapplication.ui.viewmodel.PlaylistsViewModel
import com.example.myapplication.ui.viewmodel.SearchViewModel
import com.example.myapplication.ui.viewmodel.SettingsViewModel
import com.example.myapplication.ui.viewmodel.SongViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { PlaylistViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { NewPlaylistViewModel(get()) }
    viewModel { FavoriteViewModel(get(), get()) }
    viewModel { SongViewModel(get(), get()) }
    viewModel { PlaylistsViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
}