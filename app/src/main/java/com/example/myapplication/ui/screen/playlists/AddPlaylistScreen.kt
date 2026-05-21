package com.example.myapplication.ui.screen.playlists

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.viewmodel.NewPlaylistViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import java.io.File
import java.io.IOException

@Composable
fun AddPlaylistScreen(onBack: () -> Unit) {

    val viewModel: NewPlaylistViewModel = koinViewModel()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val coverImageUri by viewModel.coverImageUri.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val localUri = saveCoverImageToInternalStorage(context, it)
            viewModel.setCoverImageUri(localUri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }


    val isButtonEnabled = title.isNotBlank()

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
                text = stringResource(R.string.back),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


        Spacer(Modifier.height(30.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(312.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        imagePickerLauncher.launch("image/*")
                    } else {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                imagePickerLauncher.launch("image/*")
                            }
                            else -> {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                }
        ) {
            if (coverImageUri != null) {
                AsyncImage(
                    model = coverImageUri!!.toUri(),
                    contentDescription = stringResource(R.string.playlist_cover),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_new_playlist),
                    contentDescription = stringResource(R.string.add_cover),
                    tint = Color(0XFFAEAFB4)
                )
            }
        }

        Spacer(Modifier.height(40.dp))


        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(stringResource(R.string.name_playlist), color = MaterialTheme.colorScheme.onBackground) },
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
            label = { Text(stringResource(R.string.description), color = MaterialTheme.colorScheme.onBackground) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0XFF3772E7),
                unfocusedBorderColor = Color(0xFFAEAFB4),
                focusedLabelColor = Color(0XFF3772E7),
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {viewModel.createNewPlaylist(title, description)
                onBack()},
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
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp)
        }

        Spacer(Modifier.height(20.dp))
    }

}

private fun saveCoverImageToInternalStorage(context: android.content.Context, sourceUri: Uri): String? {
    val directory = File(context.filesDir, "playlist_covers")
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val destinationFile = File(directory, "playlist_cover_${System.currentTimeMillis()}.jpg")

    return try {
        context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
            destinationFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        destinationFile.toUri().toString()
    } catch (e: IOException) {
        Log.e("AddPlaylistScreen", "Unable to save playlist cover", e)
        null
    }
}
