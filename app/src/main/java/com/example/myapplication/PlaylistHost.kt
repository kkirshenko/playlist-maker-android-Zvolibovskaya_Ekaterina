package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.activity.PlaylistMakerApp
import com.example.myapplication.ui.search.SearchScreen
import com.example.myapplication.ui.setting.SettingsScreen

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
            SearchScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}