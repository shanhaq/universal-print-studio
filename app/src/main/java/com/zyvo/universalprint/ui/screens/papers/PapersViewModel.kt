package com.zyvo.universalprint.ui.screens.papers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyvo.universalprint.domain.model.PaperCatalog
import com.zyvo.universalprint.domain.model.PaperCategory
import com.zyvo.universalprint.domain.model.PaperDimensions
import com.zyvo.universalprint.domain.model.PaperSize
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.repository.DocumentRepository
import com.zyvo.universalprint.domain.repository.PaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PapersUiState(
    val selectedCategory: PaperCategory? = null,
    val searchQuery: String = "",
    val customPapers: List<PaperSize> = emptyList(),
    val showCustomDialog: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PapersViewModel @Inject constructor(
    private val paperRepo: PaperRepository,
    private val docRepo: DocumentRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PapersUiState())
    val state: StateFlow<PapersUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            paperRepo.observeCustom().collect { list ->
                _state.update { it.copy(customPapers = list) }
            }
        }
    }

    val filteredPapers: List<PaperSize>
        get() {
            val s = _state.value
            val base = if (s.selectedCategory == null) PaperCatalog.all else PaperCatalog.byCategory(s.selectedCategory)
            val custom = s.customPapers
            val combined = base + custom
            return if (s.searchQuery.isBlank()) combined
            else combined.filter { it.name.contains(s.searchQuery, ignoreCase = true) }
        }

    fun setCategory(cat: PaperCategory?) = _state.update { it.copy(selectedCategory = cat) }
    fun setSearch(q: String) = _state.update { it.copy(searchQuery = q) }
    fun showCustomDialog(show: Boolean) = _state.update { it.copy(showCustomDialog = show) }

    fun createCustomPaper(name: String, widthMm: Double, heightMm: Double) {
        viewModelScope.launch {
            try {
                val paper = PaperSize(
                    id = "custom_${System.currentTimeMillis()}",
                    name = name.ifBlank { "Custom" },
                    dimensions = PaperDimensions(widthMm, heightMm),
                    category = PaperCategory.CUSTOM,
                    isCustom = true
                )
                paperRepo.saveCustom(paper)
                _state.update { it.copy(showCustomDialog = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteCustom(id: String) {
        viewModelScope.launch { paperRepo.deleteCustom(id) }
    }

    fun createDocument(paper: PaperSize, onCreated: (String) -> Unit) {
        viewModelScope.launch {
            val doc = Document(name = "Untitled", paperSize = paper)
            docRepo.save(doc)
            onCreated(doc.id)
        }
    }
}
