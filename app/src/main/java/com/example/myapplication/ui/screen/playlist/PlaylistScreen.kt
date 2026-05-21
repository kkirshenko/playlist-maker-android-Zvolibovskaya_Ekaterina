package com.example.myapplication.ui.screen.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.domain.models.Track
import com.example.myapplication.ui.screen.search.TrackListItem
import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistScreen(onBack: () -> Unit,
                   playlistId: Long,
                   navigateOnTrackDetails: (Long) -> Unit) {


    val viewModel: PlaylistViewModel = koinViewModel()
    val playlist by viewModel.getPlaylistById(playlistId).collectAsState(initial = null)
    val tracksState by viewModel.getTracksFromPlaylistById(playlistId).collectAsState(initial = null)
    val countTrack by viewModel.getCountTrackFromPlaylist(playlistId).collectAsState(initial = 0)
    val countMinute by viewModel.getMinutesFromPlaylist(playlistId).collectAsState(initial = 0)
    val tracks = tracksState
    var showDialog by remember { mutableStateOf(false) }
    var trackToDelete by remember { mutableStateOf<Track?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack, modifier = Modifier
                    .padding(0.dp, end = 24.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter =  painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }


        Spacer(Modifier.height(46.dp))


        Box(
            modifier = Modifier
                .size(375.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            playlist?.let { playlist ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = playlist.coverImageUri?.toUri(),
                        contentDescription = playlist.name,
                        modifier = Modifier.size(312.dp),
                        placeholder = painterResource(R.drawable.ic_new_playlist),
                        error = painterResource(R.drawable.ic_new_playlist),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            playlist?.name ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground)


        Spacer(Modifier.height(8.dp))

        Text(
            playlist?.description ?: "",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground)

        Spacer(Modifier.height(8.dp))


        val trackText  = when {
            countTrack%100 in 11..14 -> stringResource(R.string.track_many, countTrack)
            countTrack%10 == 1 -> stringResource(R.string.track_one, countTrack)
            countTrack%10 in 2..4 -> stringResource(R.string.track_few, countTrack)
        else-> stringResource(R.string.track_many, countTrack)
    }

        val minutesText  = when {
            countMinute%100 in 11..14 -> stringResource(R.string.minute_many, countMinute)
            countMinute%10 == 1 -> stringResource(R.string.minute_one, countMinute)
            countMinute%10 in 2..4 -> stringResource(R.string.minute_few, countMinute)
            else-> stringResource(R.string.minute_many, countMinute)
        }

        Text (stringResource(R.string.and_point_and, minutesText, trackText ),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground)

        Spacer(Modifier.height(12.dp))

        IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_option),
                    tint =  MaterialTheme.colorScheme.onBackground
                )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()){
            if(!tracks.isNullOrEmpty()){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tracks) { track ->
                            TrackListItem(track, {navigateOnTrackDetails(track.id)},
                                onLongClick = {
                                    trackToDelete = track
                                    showDialog = true
                            } )
                        }
                    }
                }
            }
        }
    if (showDialog && trackToDelete != null) {
        DeleteTrackDialog(
            onConfirm = {
                viewModel.deleteSongFromPlaylist(trackToDelete!!)
                showDialog = false
                trackToDelete = null
            },
            onDismiss = {
                showDialog = false
                trackToDelete = null
            }
        )
    }

}