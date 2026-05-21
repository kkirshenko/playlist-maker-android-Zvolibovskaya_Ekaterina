package com.example.myapplication.ui.activity


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.PlaylistHost
import com.example.myapplication.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController = navController)
        }
    }
}




@Composable
fun PlaylistMakerApp(navigateToSearch: () -> Unit,
                     navigateToSettings: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3772E7))
    ) {
        Text(
            stringResource( R.string.app_name),
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 16.dp, bottom = 30.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(vertical = 16.dp)
        )
        {
            MenuItem(icon = R.drawable.ic_search, title = stringResource( R.string.search)){ navigateToSearch()}
            MenuItem(icon = R.drawable.ic_library, title = stringResource( R.string.library)){ context -> Toast
                .makeText(context, "Нажата кнопка Плейлисты", Toast.LENGTH_SHORT)
                .show()}
            MenuItem(icon = R.drawable.ic_favorite, title = stringResource( R.string.favorite)){ context -> Toast
                .makeText(context, "Нажата кнопка Избранное", Toast.LENGTH_SHORT)
                .show()}
            MenuItem(icon = R.drawable.ic_settings, title = stringResource( R.string.setting)){ navigateToSettings()}
        }
    }
}
@Composable
fun MenuItem(icon: Int,
             title: String,
             onClick: (Context) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(context) }
            .padding(start = 28.dp, end = 28.dp, top = 20.dp, bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 22.sp,
            color = Color(0xFF1A1B22)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow),
            contentDescription = stringResource(R.string.arrow),
            tint = Color.Gray
        )
    }
}