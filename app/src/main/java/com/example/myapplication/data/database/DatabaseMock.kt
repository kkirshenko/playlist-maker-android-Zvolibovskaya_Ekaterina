package com.example.myapplication.data.database


import com.example.myapplication.data.dto.TracksSearchResponse
import com.example.myapplication.domain.models.Playlist
import com.example.myapplication.domain.models.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DatabaseMock(private val coroutine : CoroutineScope
) {

    private val historyList = mutableListOf<String>()
    private val _historyUpdates = MutableSharedFlow<Unit>()
    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf<Track>()

    fun getHistoryRequests(): Flow<List<String>> = flow {
        emit(historyList.toList())
        _historyUpdates.collect {
            emit(historyList.toList())
        }
    }

    fun addToHistory(word: String) {
        historyList.add(word)
        notifyHistoryChanged()
    }

    private fun notifyHistoryChanged() {
        coroutine.launch {
            _historyUpdates.emit(Unit)
        }
    }
    fun getTrackByID(trackId: Long): Flow<Track?> = flow{
        emit(tracks.find { it.id == trackId })
    }
    fun getPlaylist(playlistId: Long): Flow<Playlist?> = flow {
        delay(100)
        val playlist = playlists.find { it.id == playlistId }
        val playlistTracks = tracks.filter { track ->
            track.playlistId == playlistId
        }
        val playlistWithFilteredTracks = playlist?.copy(tracks = playlistTracks)

        emit(playlistWithFilteredTracks)
    }
    fun getAllPlaylists(): Flow<List<Playlist>> = flow {
        delay(500)

        val playlistsWithTracks = playlists.map { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }

        emit(playlistsWithTracks)
    }

    fun addNewPlaylist(namePlaylist: String, description: String) {
        playlists.add(
            Playlist(
                id = playlists.size.toLong() + 1,
                name = namePlaylist,
                description = description,
                tracks = emptyList()
            )
        )
    }

    fun deletePlaylistById(id: Long) {
        playlists.removeIf { it.id == id }
    }

    fun getTrackByNameAndArtist(track: Track): Flow<Track?> = flow {
        emit(tracks.find { it.trackName == track.trackName && it.artistName == track.artistName })
    }

    fun insertTrack(track: Track) {
        tracks.removeIf { it.id == track.id }
        tracks.add(track)
    }

    fun getFavoriteTracks(): Flow<List<Track>> = flow {
        delay(300)
        val favorites = tracks.filter { it.favorite }
        emit(favorites)
    }

    fun addTracks(response: TracksSearchResponse): List<Track> {

        var nextId = tracks.size.toLong()
        val newTracks = response.results.map { dto ->
            nextId++

            Track(
                id = nextId,
                trackName = dto.trackName,
                artistName = dto.artistName,
                trackTime = SimpleDateFormat("mm:ss", Locale.getDefault())
                    .format(dto.trackTimeMillis),
                image = dto.artworkUrl100,
                favorite = false,
                playlistId = 0
            )
        }
        tracks.addAll(newTracks)
        return newTracks
    }

}