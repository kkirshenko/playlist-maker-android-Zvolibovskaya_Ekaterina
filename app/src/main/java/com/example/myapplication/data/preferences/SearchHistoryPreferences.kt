package com.example.myapplication.data.preferences


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val MAX_ENTRIES = 10
private const val SEPARATOR = ","

private val preferencesKey = stringPreferencesKey("search_history")

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {

    fun addEntry(word: String) {
        if (word.isEmpty()) {
            return
        }

        coroutineScope.launch {
            dataStore.updateData { preferences ->
                val historyString = preferences[preferencesKey].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val subList = history.take(MAX_ENTRIES)
                val updatedString = subList.joinToString(SEPARATOR)


                preferences.toMutablePreferences().apply {
                    this[preferencesKey] = updatedString
                }

            }
        }
    }

    fun getEntries(): Flow<List<String>> {
        return dataStore.data
            .map { preferences ->
                val historyString = preferences[preferencesKey].orEmpty()
                if (historyString.isEmpty()) emptyList()
                else historyString.split(SEPARATOR)
            }

    }
}