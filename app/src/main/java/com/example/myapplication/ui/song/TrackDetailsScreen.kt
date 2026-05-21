package com.example.myapplication.ui.song

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
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
import com.example.myapplication.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun TrackDetailsScreen(
    onBack: () -> Unit,
    trackId: Long,
    callback: () -> Unit
) {
    val viewModel: PlaylistViewModel = koinViewModel()
    val trackState by viewModel.getTrackById(trackId).collectAsState(initial = null)
    val track = trackState

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
                TrackImage(
                    track?.image, modifier = Modifier .size(375.dp))
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            track?.trackName ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold)


        Spacer(Modifier.height(12.dp))


        Text(
            track?.artistName ?: "",
            fontSize = 14.sp)


        Spacer(Modifier.height(54.dp))


        Row(
            modifier = Modifier
            .fillMaxWidth()){

            Box(modifier = Modifier.fillMaxWidth()) {

                FloatingActionButton(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    onClick = {callback()},
                    containerColor = Color(0xFFBDBDBD),
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_playlist),
                        contentDescription = stringResource(R.string.add_in_playlist),
                        tint = Color.White
                    )
                }

                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.TopEnd),
                    onClick = {if (track?.favorite == true) viewModel.toggleFavorite(track.id,false) else viewModel.toggleFavorite(track!!.id,true)},
                    containerColor = Color(0xFFBDBDBD),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = if(track?.favorite == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )

                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {


            Text(
                stringResource(R.string.duration),
                fontSize = 13.sp,
                color = Color(0xFFAEAFB4)
            )


            Text(
                track?.trackTime ?: "",
                fontSize = 13.sp,

            )
        }
    }
}

