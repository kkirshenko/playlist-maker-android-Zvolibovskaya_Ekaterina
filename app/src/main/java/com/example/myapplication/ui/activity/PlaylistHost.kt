package com.example.myapplication.ui.activity

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.activity.Screen
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
                navigateToSettings = { navController.navigate(Screen.SETTINGS.route) })
        }
        composable(Screen.SEARCH.route) {
            val searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.getViewModelFactory())
            SearchScreen(onBack = { navController.popBackStack() }, viewModel = searchViewModel)
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}