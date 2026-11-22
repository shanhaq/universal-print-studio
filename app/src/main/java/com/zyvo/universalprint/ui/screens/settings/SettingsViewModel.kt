package com.zyvo.universalprint.ui.screens.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val darkTheme: Boolean = false,
    val defaultPaperId: String = "iso-a4"
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = dataStore.data.map { prefs ->
        SettingsUiState(
            darkTheme = prefs[Keys.DARK_THEME] ?: false,
            defaultPaperId = prefs[Keys.DEFAULT_PAPER] ?: "iso-a4"
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    fun setDarkTheme(on: Boolean) {
        viewModelScope.launch { dataStore.edit { it[Keys.DARK_THEME] = on } }
    }

    fun setDefaultPaper(id: String) {
        viewModelScope.launch { dataStore.edit { it[Keys.DEFAULT_PAPER] = id } }
    }

    private object Keys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val DEFAULT_PAPER = stringPreferencesKey("default_paper")
    }
}
