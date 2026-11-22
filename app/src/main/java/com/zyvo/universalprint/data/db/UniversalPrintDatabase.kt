package com.zyvo.universalprint.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zyvo.universalprint.data.db.dao.DocumentDao
import com.zyvo.universalprint.data.db.dao.LayerDao
import com.zyvo.universalprint.data.db.dao.PaperSizeDao
import com.zyvo.universalprint.data.db.dao.PrinterDao
import com.zyvo.universalprint.data.db.entity.DocumentEntity
import com.zyvo.universalprint.data.db.entity.LayerEntity
import com.zyvo.universalprint.data.db.entity.PaperSizeEntity
import com.zyvo.universalprint.data.db.entity.PrinterEntity

@Database(
    entities = [
        PaperSizeEntity::class,
        DocumentEntity::class,
        LayerEntity::class,
        PrinterEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class UniversalPrintDatabase : RoomDatabase() {
    abstract fun paperSizeDao(): PaperSizeDao
    abstract fun documentDao(): DocumentDao
    abstract fun layerDao(): LayerDao
    abstract fun printerDao(): PrinterDao
}
