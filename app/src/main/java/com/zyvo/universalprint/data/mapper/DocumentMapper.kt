package com.zyvo.universalprint.data.mapper

import com.zyvo.universalprint.data.db.entity.DocumentEntity
import com.zyvo.universalprint.data.db.entity.LayerEntity
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.model.DocumentLayer
import com.zyvo.universalprint.domain.model.LayerType
import com.zyvo.universalprint.domain.model.PaperCatalog
import com.zyvo.universalprint.domain.model.PaperSize

fun DocumentEntity.toDomain(layers: List<LayerEntity>): Document {
    val paper = PaperCatalog.byId(paperSizeId)
        ?: PaperSize(id = paperSizeId, name = paperSizeId, dimensions = com.zyvo.universalprint.domain.model.PaperDimensions(210.0, 297.0), category = com.zyvo.universalprint.domain.model.PaperCategory.CUSTOM, isCustom = true)
    return Document(
        id = id,
        name = name,
        paperSize = paper,
        layers = layers.map { it.toDomain() },
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun LayerEntity.toDomain(): DocumentLayer = DocumentLayer(
    id = id,
    type = LayerType.valueOf(type),
    xMm = xMm, yMm = yMm, widthMm = widthMm, heightMm = heightMm,
    rotationDeg = rotationDeg, zIndex = zIndex, visible = visible,
    text = text, fontSizePt = fontSizePt, fontFamily = fontFamily,
    bold = bold, italic = italic, textColor = textColor,
    imageUri = imageUri, shapeType = shapeType,
    fillColor = fillColor, strokeColor = strokeColor, strokeWidthMm = strokeWidthMm
)

fun Document.toEntity(): DocumentEntity = DocumentEntity(
    id = id, name = name, paperSizeId = paperSize.id,
    createdAt = createdAt, updatedAt = updatedAt
)

fun DocumentLayer.toEntity(documentId: String): LayerEntity = LayerEntity(
    id = id, documentId = documentId, type = type.name,
    xMm = xMm, yMm = yMm, widthMm = widthMm, heightMm = heightMm,
    rotationDeg = rotationDeg, zIndex = zIndex, visible = visible,
    text = text, fontSizePt = fontSizePt, fontFamily = fontFamily,
    bold = bold, italic = italic, textColor = textColor,
    imageUri = imageUri, shapeType = shapeType,
    fillColor = fillColor, strokeColor = strokeColor, strokeWidthMm = strokeWidthMm
)
