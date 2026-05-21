package com.example.myapplication.ui.screen.playlists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.domain.models.Playlist
import org.koin.androidx.compose.koinViewModel
import com.example.myapplication.ui.viewmodel.PlaylistsViewModel
import androidx.core.net.toUri

@Composable
fun PlaylistListItem(playlist: Playlist,
                     onClick: () -> Unit) {

    val playlistsViewModel: PlaylistsViewModel = koinViewModel()
    val count by playlistsViewModel.getCountTrackFromPlaylist(playlist.id).collectAsState(0)
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke() }),
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.size(48.dp)
            .clip(RoundedCornerShape(6.dp))) {
            AsyncImage(
                model = playlist.coverImageUri!!.toUri(),
                contentDescription = playlist.name,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(id = R.drawable.ic_new_playlist),
                error = painterResource(id = R.drawable.ic_new_playlist),
                contentScale = ContentScale.Crop)
        }


        Column(
            modifier = Modifier.Companion.weight(1f)
            .padding(start = 6.dp),
            horizontalAlignment = Alignment.Companion.Start
        ) {
            Text(playlist.name, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
            val text = when {
                count%100 in 11..14 -> stringResource(R.string.track_many, count)
                count%10 == 1 -> stringResource(R.string.track_one, count)
                count%10 in 2..4 -> stringResource(R.string.track_few, count)
                else-> stringResource(R.string.track_many, count)
            }
            Text(text, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}