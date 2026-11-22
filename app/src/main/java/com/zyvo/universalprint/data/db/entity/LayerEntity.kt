package com.zyvo.universalprint.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "layers",
    foreignKeys = [ForeignKey(
        entity = DocumentEntity::class,
        parentColumns = ["id"],
        childColumns = ["documentId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("documentId")]
)
data class LayerEntity(
    @PrimaryKey val id: String,
    val documentId: String,
    val type: String,
    val xMm: Double,
    val yMm: Double,
    val widthMm: Double,
    val heightMm: Double,
    val rotationDeg: Double,
    val zIndex: Int,
    val visible: Boolean,
    val text: String?,
    val fontSizePt: Double?,
    val fontFamily: String?,
    val bold: Boolean,
    val italic: Boolean,
    val textColor: Long?,
    val imageUri: String?,
    val shapeType: String?,
    val fillColor: Long?,
    val strokeColor: Long?,
    val strokeWidthMm: Double?
)
