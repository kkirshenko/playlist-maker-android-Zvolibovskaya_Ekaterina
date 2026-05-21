package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen()
        }
    }
}

@Preview()
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val activity = context as? Activity
    var isDarkTheme by remember { mutableStateOf(false) }

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
                onClick = { activity?.finish() }, modifier = Modifier
                    .padding(0.dp, end = 24.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.back),
                    tint = Color.Black,
                )
            }

            Text(
                text = stringResource(R.string.setting),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 17.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.dark_theme), fontSize = 16.sp, color = Color.Black)
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it }
            )
        }

        // Поделиться приложением
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.share_app), fontSize = 16.sp, color = Color.Black)
            Icon(painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.share_app),
                tint = Color.Gray)
        }

        // Написать в поддержку
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Написать в поддержку", fontSize = 16.sp, color = Color.Black)
            Icon(painter = painterResource(id = R.drawable.ic_support),
                contentDescription = "Поддержка",
                tint = Color.Gray)
        }

        // Пользовательское соглашение
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Пользовательское соглашение", fontSize = 16.sp, color = Color.Black)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = stringResource(R.string.arrow),
                tint = Color.Gray
            )
        }
    }
}