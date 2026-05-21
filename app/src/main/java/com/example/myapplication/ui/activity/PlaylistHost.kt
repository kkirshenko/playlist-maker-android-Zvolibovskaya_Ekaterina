package com.example.myapplication.ui.activity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.playlist.BottomSheetExample
import com.example.myapplication.ui.playlist.PlaylistScreen
import com.example.myapplication.ui.search.SearchScreen
import com.example.myapplication.ui.setting.SettingsScreen
import com.example.myapplication.ui.viewmodel.SearchViewModel

@Composable
fun PlaylistHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route,
    ) {
        composable(Screen.MAIN.route) {
            PlaylistMakerApp(
                navigateToSearch = { navController.navigate(Screen.SEARCH.route) },
                navigateToSettings = { navController.navigate(Screen.SETTINGS.route) },
                navigateToPlaylist = { navController.navigate(Screen.PLAYLIST.route) })
        }
        composable(Screen.SEARCH.route) {
            val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(onBack = { navController.popBackStack() }, viewModel = searchViewModel)
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.PLAYLIST.route) {
            var showBottomSheet by remember { mutableStateOf(false) }

            PlaylistScreen(
                callback = { showBottomSheet = true },
                onBack = { navController.popBackStack() }
            )

            BottomSheetExample(
                isShowPanel = showBottomSheet,
                onDismissRequest = { showBottomSheet = false },
                content = "Это работает"
            )
        }
    }
}