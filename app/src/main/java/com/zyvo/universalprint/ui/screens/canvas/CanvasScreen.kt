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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zyvo.universalprint.domain.model.DocumentLayer
import com.zyvo.universalprint.domain.model.LayerType
import com.zyvo.universalprint.render.canvas.DocumentRenderer
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CanvasScreen(
    documentId: String,
    onBack: () -> Unit,
    onPreview: () -> Unit
) {
    val vm: CanvasViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.document?.name ?: "Canvas") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = {
                    IconButton(onClick = {
                        vm.addLayer(
                            DocumentLayer(
                                id = UUID.randomUUID().toString(),
                                type = LayerType.TEXT,
                                xMm = 20.0, yMm = 20.0,
                                widthMm = 100.0, heightMm = 10.0,
                                text = "Hello Print", fontSizePt = 14.0
                            )
                        )
                    }) { Icon(Icons.Default.TextFields, "Add Text") }
                    IconButton(onClick = onPreview) { Icon(Icons.Default.Visibility, "Preview") }
                }
            )
        }
    ) { pad ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.document == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.error ?: "No document")
            }
        } else {
            val doc = state.document!!
            val bmp: Bitmap = DocumentRenderer.render(doc, dpi = 120)
            Column(modifier = Modifier.fillMaxSize().padding(pad)) {
                Text(
                    "${doc.paperSize.name}: ${doc.paperSize.dimensions.widthMm} x ${doc.paperSize.dimensions.heightMm} mm",
                    modifier = Modifier.padding(8.dp)
                )
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp).background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(bitmap = bmp.asImageBitmap(), contentDescription = "Canvas preview")
                }
                Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onPreview) { Text("Preview") }
                }
            }
        }
    }
}
