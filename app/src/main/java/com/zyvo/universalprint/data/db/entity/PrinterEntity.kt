package com.zyvo.universalprint.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "printers")
data class PrinterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val address: String?,
    val capabilitiesJson: String?
)
