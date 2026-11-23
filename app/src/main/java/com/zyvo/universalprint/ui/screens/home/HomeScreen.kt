package com.zyvo.universalprint.ui.screens.home

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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.model.PaperCatalog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNewDocument: (String) -> Unit,
    onOpenCanvas: (String) -> Unit,
    onOpenPapers: () -> Unit,
    onOpenPrinters: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val vm: HomeViewModel = hiltViewModel()
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Universal Print Studio") },
                actions = {
                    IconButton(onClick = onOpenPapers) { Icon(Icons.Default.Description, "Papers") }
                    IconButton(onClick = onOpenPrinters) { Icon(Icons.Default.Print, "Printers") }
                    IconButton(onClick = onOpenSettings) { Icon(Icons.Default.Settings, "Settings") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                vm.createDocument("iso-a4") { id -> onOpenCanvas(id) }
            }) { Icon(Icons.Default.Add, "New") }
        }
    ) { pad ->
        if (state.documents.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(pad),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No documents yet. Tap + to create one.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(pad).padding(8.dp)) {
                items(state.documents, key = { it.id }) { doc ->
                    DocumentCard(doc = doc, onClick = { onOpenCanvas(doc.id) })
                }
            }
        }
    }
}

@Composable
private fun DocumentCard(doc: Document, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(doc.name, style = MaterialTheme.typography.titleMedium)
            Text(
                "${doc.paperSize.name} • ${doc.layers.size} layers",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(doc.updatedAt)),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
