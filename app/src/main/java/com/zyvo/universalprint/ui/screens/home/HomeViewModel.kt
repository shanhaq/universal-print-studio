package com.zyvo.universalprint.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val documents: List<Document> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: DocumentRepository
) : ViewModel() {
    val uiState: StateFlow<HomeUiState> = repo.observeAll()
        .map { HomeUiState(documents = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState(isLoading = true))

    fun deleteDocument(id: String) {
        viewModelScope.launch {
            try { repo.delete(id) } catch (e: Exception) { /* surface via state in real impl */ }
        }
    }
}
