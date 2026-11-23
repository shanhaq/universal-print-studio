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
    val standardCode: String? = null,
    val region: String? = null,
    val sourceName: String? = null,
    val sourceUrl: String? = null,
    val revision: String? = null,
    val isStandard: Boolean = false,
    val isCustom: Boolean = false,
    val isFavorite: Boolean = false,
    val isRollMedia: Boolean = false,
    val defaultOrientation: String = "EITHER",
    val version: Int = 1,
    val metadataJson: String? = null
)
