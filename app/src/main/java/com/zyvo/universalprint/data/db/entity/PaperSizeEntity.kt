package com.zyvo.universalprint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paper_sizes")
data class PaperSizeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val widthMm: Double,
    val heightMm: Double,
    val category: String,
    val version: Int,
    val isCustom: Boolean
)
