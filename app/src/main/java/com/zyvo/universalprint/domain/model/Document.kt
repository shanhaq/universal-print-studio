package com.zyvo.universalprint.domain.model

import java.util.UUID

enum class LayerType { TEXT, IMAGE, SHAPE, LINE }

data class DocumentLayer(
    val id: String = UUID.randomUUID().toString(),
    val type: LayerType,
    val xMm: Double,
    val yMm: Double,
    val widthMm: Double,
    val heightMm: Double,
    val rotationDeg: Double = 0.0,
    val zIndex: Int = 0,
    val visible: Boolean = true,
    // Text-specific
    val text: String? = null,
    val fontSizePt: Double? = null,
    val fontFamily: String? = null,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val textColor: Long? = null, // ARGB
    // Image-specific
    val imageUri: String? = null,
    // Shape-specific
    val shapeType: String? = null,
    val fillColor: Long? = null,
    val strokeColor: Long? = null,
    val strokeWidthMm: Double? = null
)

data class Document(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val paperSize: PaperSize,
    val layers: List<DocumentLayer> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
