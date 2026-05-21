package com.example.myapplication.ui.screen.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.screen.search.TrackListItem
import com.example.myapplication.ui.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    navigateOnTrackDetails: (Long) -> Unit,
) {
    val viewModel: FavoriteViewModel = koinViewModel()
    val favoriteList by viewModel.favoriteList.collectAsState(emptyList())
    val isDark by viewModel.isDarkTheme.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = stringResource(R.string.favorite_tracks),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (favoriteList.isNotEmpty()) {

            Spacer(modifier = Modifier.height(28.dp))

            LazyColumn {
                items(favoriteList) { track ->
                    TrackListItem(
                        track = track,
                        onTrackClick = { navigateOnTrackDetails(track.id) },
                        onLongClick = {
                            viewModel.toggleFavorite(track.id, false)
                        }
                    )

                }
            }
        } else{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 210.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = if(isDark) R.drawable.ic_error_dark else R.drawable.ic_nothing),
                    contentDescription = stringResource(R.string.empty_favorite_tracks),
                    modifier = Modifier
                        .size(120.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.empty_favorite_tracks),
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
