package com.zyvo.universalprint.ui.screens.canvas

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.render.canvas.DocumentRenderer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasScreen(documentId: String, onBack: () -> Unit, onPreview: () -> Unit) {
    val vm: CanvasViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.document?.name ?: "Canvas") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                actions = { Button(onClick = onPreview) { Text("Preview") } }
            )
        }
    ) { pad ->
        Box(modifier = Modifier.fillMaxSize().padding(pad)) {
            val doc = state.document
            when {
                state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                doc == null -> Text(state.error ?: "Not found", modifier = Modifier.align(Alignment.Center))
                else -> CanvasContent(doc, onPreview, Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun CanvasContent(doc: Document, onPreview: () -> Unit, modifier: Modifier) {
    var bmp by remember { mutableStateOf<Bitmap?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(doc.id) {
        try {
            bmp = DocumentRenderer.render(doc, dpi = 120)
        } catch (t: Throwable) {
            error = t.message ?: "Render failed"
        }
    }
    Column(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("${doc.paperSize.name}: ${doc.paperSize.dimensions.widthMm} x ${doc.paperSize.dimensions.heightMm} mm")
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f).background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            val b = bmp
            when {
                error != null -> Text(error ?: "Error", color = MaterialTheme.colorScheme.error)
                b != null -> Image(bitmap = b.asImageBitmap(), contentDescription = "Canvas preview")
                else -> CircularProgressIndicator()
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onPreview) { Text("Preview") }
        }
    }
}
