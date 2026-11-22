package com.zyvo.universalprint.ui.screens.papers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zyvo.universalprint.domain.model.PaperCategory
import com.zyvo.universalprint.domain.model.PaperSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PapersScreen(
    onBack: () -> Unit,
    onDocumentCreated: (String) -> Unit = {}
) {
    val vm: PapersViewModel = hiltViewModel()
    val state by vm.state.collectAsState()
    var customName by remember { mutableStateOf("") }
    var customW by remember { mutableStateOf("") }
    var customH by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paper Sizes") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.showCustomDialog(true) }) { Icon(Icons.Default.Add, "Custom") }
        }
    ) { pad ->
        Column(modifier = Modifier.fillMaxSize().padding(pad)) {
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.selectedCategory == null,
                    onClick = { vm.setCategory(null) },
                    label = { Text("All") }
                )
                PaperCategory.values().forEach { cat ->
                    FilterChip(
                        selected = state.selectedCategory == cat,
                        onClick = { vm.setCategory(cat) },
                        label = { Text(cat.name) }
                    )
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(vm.filteredPapers, key = { it.id }) { paper ->
                    Card(onClick = { vm.createDocument(paper, onDocumentCreated) }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(paper.name, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${paper.dimensions.widthMm} x ${paper.dimensions.heightMm} mm",
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (paper.isCustom) {
                                TextButton(onClick = { vm.deleteCustom(paper.id) }) { Text("Delete") }
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showCustomDialog) {
        AlertDialog(
            onDismissRequest = { vm.showCustomDialog(false) },
            title = { Text("Custom Paper Size") },
            text = {
                Column {
                    OutlinedTextField(value = customName, onValueChange = { customName = it }, label = { Text("Name") })
                    OutlinedTextField(value = customW, onValueChange = { customW = it }, label = { Text("Width (mm)") })
                    OutlinedTextField(value = customH, onValueChange = { customH = it }, label = { Text("Height (mm)") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val w = customW.toDoubleOrNull()
                    val h = customH.toDoubleOrNull()
                    if (w != null && h != null && w > 0 && h > 0) {
                        vm.createCustomPaper(customName, w, h)
                    }
                }) { Text("Create") }
            },
            dismissButton = { TextButton(onClick = { vm.showCustomDialog(false) }) { Text("Cancel") } }
        )
    }
}
