package com.example.myapplication.ui.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.PlaylistHost
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: SettingsViewModel = koinViewModel()
            val isDark by viewModel.isDarkTheme.collectAsState()
            val navController = rememberNavController()

            MyApplicationTheme(darkTheme = isDark){
                PlaylistHost(navController = navController)}
        }
    }
}
