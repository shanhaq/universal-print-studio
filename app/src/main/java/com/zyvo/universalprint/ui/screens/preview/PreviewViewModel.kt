package com.zyvo.universalprint.ui.screens.preview

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyvo.universalprint.domain.repository.DocumentRepository
import com.zyvo.universalprint.render.canvas.DocumentRenderer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PreviewUiState(
    val bitmap: Bitmap? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class PreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val docRepo: DocumentRepository
) : ViewModel() {
    private val documentId: String = checkNotNull(savedStateHandle["documentId"])
    private val _state = MutableStateFlow(PreviewUiState())
    val state: StateFlow<PreviewUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val doc = docRepo.getById(documentId)
            if (doc == null) {
                _state.value = PreviewUiState(isLoading = false, error = "Document not found")
                return@launch
            }
            val bmp = DocumentRenderer.render(doc, dpi = 150)
            _state.value = PreviewUiState(bitmap = bmp, isLoading = false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        _state.value.bitmap?.recycle()
    }
}
