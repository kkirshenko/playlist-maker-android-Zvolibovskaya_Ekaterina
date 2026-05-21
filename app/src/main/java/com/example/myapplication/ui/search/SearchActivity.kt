package com.example.myapplication.ui.search

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.dto.SearchState
import com.example.myapplication.ui.viewmodel.SearchViewModel


@Composable
fun SearchScreen(
    onBack: () -> Unit,
    navigateOnTrackDetails : (Long) -> Unit,
) {
    val viewModel: SearchViewModel = koinViewModel()
    val screenState by viewModel.searchScreenState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Log.i("anc", "abc2")
    LaunchedEffect(searchQuery) {
        viewModel.updateQuery(searchQuery)
    }

    LaunchedEffect(screenState) {
        when (screenState) {
            is SearchState.Success -> {
                focusManager.clearFocus()
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getHistoryList().collect {  list: List<String> ->
            historyList = list.reversed()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { onBack() },
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
                text = stringResource(R.string.search),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(color = Color(0xFFE7E8EB),
                    shape = if (isFocused && searchQuery.isEmpty() && historyList.isNotEmpty()) {
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                } else {
                    RoundedCornerShape(8.dp)
                }),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search),
                    tint = Color(0xFFAEAFB4),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            viewModel.updateQuery(searchQuery)
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = stringResource(R.string.search),
                                color = Color(0xFFAEAFB4),
                                fontSize = 16.sp,
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.weight(1f)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        }
                )

                AnimatedVisibility(
                    visible = searchQuery.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.cancel),
                        tint = Color(0xFFAEAFB4),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                searchQuery = ""
                                viewModel.clearSearch()

                            }
                    )

                }

            }
        }
        if (isFocused && searchQuery.isEmpty() && historyList.isNotEmpty()) {
            HistoryRequests(
                historyList = historyList,
                onClick = { word ->
                    searchQuery = word
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (screenState) {
            is SearchState.Initial -> {

                if (!searchQuery.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

            }

            is SearchState.Searching -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                    val tracks = (screenState as SearchState.Success).list
                    if (!tracks.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(tracks) { track ->
                                TrackListItem(track) {navigateOnTrackDetails(track.id)}
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 210.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_nothing),
                                contentDescription = stringResource(R.string.nothing_search),
                                modifier = Modifier
                                    .size(120.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(R.string.nothing_search),
                                fontSize = 19.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
            }

            is SearchState.Fail -> {
                val error = (screenState as SearchState.Fail).error
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 210.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                )  {
                    Image(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = stringResource(R.string.error_network),
                        modifier = Modifier
                            .size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.error_network),
                        fontSize = 19.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.error_network_2),
                        fontSize = 19.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
