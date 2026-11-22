package com.zyvo.universalprint.ui.screens.canvas

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.model.DocumentLayer
import com.zyvo.universalprint.domain.model.PaperCatalog
import com.zyvo.universalprint.domain.repository.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CanvasUiState(
    val document: Document? = null,
    val isLoading: Boolean = true,
    val selectedLayerId: String? = null,
    val error: String? = null
)

@HiltViewModel
class CanvasViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val docRepo: DocumentRepository
) : ViewModel() {
    private val documentId: String = checkNotNull(savedStateHandle["documentId"])

    private val _state = MutableStateFlow(CanvasUiState())
    val state: StateFlow<CanvasUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val doc = docRepo.getById(documentId)
            _state.update { it.copy(document = doc, isLoading = false) }
            if (doc == null) _state.update { it.copy(error = "Document not found") }
        }
    }

    fun addLayer(layer: DocumentLayer) {
        _state.update { s ->
            val d = s.document ?: return@update s
            s.copy(document = d.copy(layers = d.layers + layer, updatedAt = System.currentTimeMillis()))
        }
        persist()
    }

    fun removeLayer(id: String) {
        _state.update { s ->
            val d = s.document ?: return@update s
            s.copy(document = d.copy(layers = d.layers.filterNot { it.id == id }, updatedAt = System.currentTimeMillis()))
        }
        persist()
    }

    fun selectLayer(id: String?) = _state.update { it.copy(selectedLayerId = id) }

    private fun persist() {
        viewModelScope.launch {
            _state.value.document?.let { docRepo.save(it) }
        }
    }
}
