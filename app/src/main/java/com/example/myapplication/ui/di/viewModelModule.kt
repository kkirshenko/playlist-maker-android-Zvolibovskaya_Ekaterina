package com.example.myapplication.ui.di

import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import com.example.myapplication.ui.viewmodel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {  PlaylistViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
}