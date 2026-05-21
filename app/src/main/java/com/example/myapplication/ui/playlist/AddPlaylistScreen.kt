package com.example.myapplication.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun AddPlaylistScreen(onBack: () -> Unit) {

    val viewModel: PlaylistViewModel = koinViewModel()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val isButtonEnabled = title.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                    tint = Color.Black
                )
            }

            Text(
                text = stringResource(R.string.new_playlist),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }


        Spacer(Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .size(312.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_new_playlist),
                    contentDescription = stringResource(R.string.back),
                    tint = Color(0XFFAEAFB4)
                )
            }
        }

        Spacer(Modifier.height(40.dp))


        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.name_playlist)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0XFF3772E7),
                unfocusedBorderColor = Color(0xFFAEAFB4),
                focusedLabelColor = Color(0XFF3772E7),
            )
        )

        Spacer(Modifier.height(20.dp))


        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.description)) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0XFF3772E7),
                unfocusedBorderColor = Color(0xFFAEAFB4),
                focusedLabelColor = Color(0XFF3772E7),
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {viewModel.createNewPlayList(title, description)
                onBack},
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                disabledBackgroundColor = Color(0xFFAEAFB4),
                backgroundColor = Color(0xFF3772E7)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
        ) {
            Text(stringResource(R.string.create),
                color = Color.White,
                fontSize = 16.sp)
        }

        Spacer(Modifier.height(20.dp))
    }

}
