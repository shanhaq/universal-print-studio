package com.zyvo.universalprint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val name: String,
    val paperSizeId: String,
    val createdAt: Long,
    val updatedAt: Long
)
