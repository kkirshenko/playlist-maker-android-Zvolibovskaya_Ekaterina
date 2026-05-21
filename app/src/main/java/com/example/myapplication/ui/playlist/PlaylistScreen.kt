package com.example.myapplication.ui.playlist

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.search.TrackListItem
import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PlaylistScreen(onBack: () -> Unit,
                   playlistId: Long,
                   navigateOnTrackDetails: (Long) -> Unit) {
    val viewModel: PlaylistViewModel = koinViewModel()
    val playlistState by viewModel.getPlaylistById(playlistId).collectAsState(initial = null)
    val playlist = playlistState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    tint = Color.Black,
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_new_playlist),
                    contentDescription = stringResource(R.string.new_playlist),
                    tint = Color(0XFFAEAFB4)
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            playlist?.name ?: "",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)


        Spacer(Modifier.height(8.dp))


        Text(
            playlist?.description ?: "",
            fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Еще опции"
                )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()){
            val tracks = playlist?.tracks
            tracks?.isEmpty()?.let {
                if (!it) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tracks) { track ->
                            TrackListItem(track) {navigateOnTrackDetails(track.id)}
                        }
                    }
                }
            }



        }

    }

}