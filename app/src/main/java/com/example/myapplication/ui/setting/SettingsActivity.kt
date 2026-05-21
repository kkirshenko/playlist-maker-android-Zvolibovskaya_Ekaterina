package com.example.myapplication.ui.setting

import android.content.Intent
import androidx.compose.foundation.background
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.myapplication.R


@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
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
                onClick = { onBack() }, modifier = Modifier
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
                onCheckedChange = { isDarkTheme = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF3772E7),
                    checkedTrackColor = Color(0xFF3772E7)
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp)
                .clickable{
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            context.getString(R.string.share_message)
                        )
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            context.getString(R.string.share_app)
                        )
                    )
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.share_app), fontSize = 16.sp, color = Color.Black)
            Icon(painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.share_app),
                tint = Color.Gray)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp)
                .clickable {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = ("mailto:" + context.getString(R.string.dev_mail)).toUri()
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.email_body))
                }
                context.startActivity(emailIntent)
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.write_to_support), fontSize = 16.sp, color = Color.Black)
            Icon(painter = painterResource(id = R.drawable.ic_support),
                contentDescription = stringResource(R.string.write_to_support),
                tint = Color.Gray)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical  = 21.dp)
                .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    context.getString(R.string.user_agreement_link).toUri()
                )
                context.startActivity(browserIntent)
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.user_agreement), fontSize = 16.sp, color = Color.Black)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = stringResource(R.string.arrow),
                tint = Color.Gray
            )
        }
    }
}