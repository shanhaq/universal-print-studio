package com.zyvo.universalprint.ui.screens.printers

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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.zyvo.universalprint.domain.model.Printer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintersScreen(onBack: () -> Unit) {
    val vm: PrintersViewModel = hiltViewModel()
    val state by vm.state.collectAsState()
    var netName by remember { mutableStateOf("") }
    var netAddr by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Printers") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = {
                    IconButton(onClick = { vm.discoverAll() }) { Icon(Icons.Default.Refresh, "Discover") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { vm.showAddNetworkDialog(true) }) { Icon(Icons.Default.Add, "Add") }
        }
    ) { pad ->
        Column(modifier = Modifier.fillMaxSize().padding(pad)) {
            if (state.isDiscovering) Text("Discovering...", modifier = Modifier.padding(8.dp))
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp)) }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(state.printers, key = { it.id }) { p ->
                    PrinterCard(p, onDelete = { vm.deletePrinter(p.id) })
                }
            }
        }
    }

    if (state.showAddNetworkDialog) {
        AlertDialog(
            onDismissRequest = { vm.showAddNetworkDialog(false) },
            title = { Text("Add Network Printer") },
            text = {
                Column {
                    OutlinedTextField(value = netName, onValueChange = { netName = it }, label = { Text("Name") })
                    OutlinedTextField(value = netAddr, onValueChange = { netAddr = it }, label = { Text("IP:port (default 9100)") })
                }
            },
            confirmButton = {
                TextButton(onClick = { vm.addNetworkPrinter(netName, netAddr) }) { Text("Add") }
            },
            dismissButton = { TextButton(onClick = { vm.showAddNetworkDialog(false) }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun PrinterCard(p: Printer, onDelete: () -> Unit) {
    Card(modifier = androidx.compose.ui.Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(p.name, style = MaterialTheme.typography.titleMedium)
                Text("${p.type} • ${p.address ?: "-"}", style = MaterialTheme.typography.bodySmall)
                Text("Status: ${p.status}", style = MaterialTheme.typography.labelSmall)
            }
            TextButton(onClick = onDelete) { Text("Remove") }
        }
    }
}
