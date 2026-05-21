package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screen.favorites.FavoritesScreen
import com.example.myapplication.ui.screen.playlist.PlaylistScreen
import com.example.myapplication.ui.screen.playlists.AddPlaylistScreen
import com.example.myapplication.ui.screen.playlists.PlaylistsScreen
import com.example.myapplication.ui.screen.PlaylistMakerApp
import com.example.myapplication.ui.screen.search.SearchScreen
import com.example.myapplication.ui.screen.song.TrackDetailsScreen
import com.example.myapplication.ui.screen.setting.SettingsScreen
import com.example.myapplication.ui.screen.song.BottomSheetExample



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
                navigateToPlaylists = { navController.navigate(Screen.PLAYLISTS.route) },
                navigateToFavorites = { navController.navigate(Screen.FAVORITES.route) })
        }


        composable(Screen.SEARCH.route) {
            SearchScreen(onBack = { navController.popBackStack() }, navigateOnTrackDetails = {trackId ->
                navController.navigate("${Screen.TRACKDETAIL.route}/$trackId")
            })
        }


        composable(Screen.SETTINGS.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }


        composable(Screen.PLAYLISTS.route) {
            PlaylistsScreen(onBack = { navController.popBackStack() },
                addNewPlaylist = { navController.navigate(Screen.NEW_PLAYLIST.route)},
                navigateToPlaylist = {playlistId ->
                    navController.navigate("${Screen.PLAYLISTDETAIL.route}/$playlistId")})
        }


        composable(Screen.FAVORITES.route) {
            FavoritesScreen ( onBack = { navController.popBackStack()}, navigateOnTrackDetails = {trackId ->
                navController.navigate("${Screen.TRACKDETAIL.route}/$trackId")})
        }


        composable(Screen.NEW_PLAYLIST.route){
            AddPlaylistScreen(onBack = { navController.popBackStack() })
        }


        composable(
            route = "${Screen.TRACKDETAIL.route}/{trackId}",
            arguments = listOf(
                navArgument("trackId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getInt("trackId") ?: 0
            var showBottomSheet by remember { mutableStateOf(false) }

            TrackDetailsScreen(
                onBack = { navController.popBackStack() },
                trackId = trackId.toLong(),
                callback = { showBottomSheet = true }
            )

            BottomSheetExample(
                isShowPanel = showBottomSheet,
                onDismissRequest = { showBottomSheet = false },
                trackId = trackId.toLong()
            )
        }

        composable(
            route = "${Screen.PLAYLISTDETAIL.route}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getInt("playlistId") ?: 0

            PlaylistScreen (
                onBack = { navController.popBackStack() },
                playlistId = playlistId.toLong(),
                navigateOnTrackDetails = {trackId ->
                    navController.navigate("${Screen.TRACKDETAIL.route}/$trackId")
                }
            )

        }
    }
}