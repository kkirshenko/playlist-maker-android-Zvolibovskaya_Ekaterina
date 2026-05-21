package com.example.myapplication.ui.song

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.playlist.PlaylistListItem
import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample(
    isShowPanel: Boolean,
    onDismissRequest: () -> Unit,
    trackId: Long
) {

    val playlistViewModel: PlaylistViewModel = koinViewModel()
    val playlists by playlistViewModel.playlists.collectAsState(emptyList())
    val sheetState = rememberModalBottomSheetState()
    val track = playlistViewModel.getTrackById(trackId)


    if (isShowPanel) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.add_in_playlist),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 19.sp
                )

                Spacer(Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                       items(playlists) { playlist ->
                            PlaylistListItem(playlist = playlist) {
                               track?.playlistId = playlist.id
                            }
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }

            }
        }
    }
}