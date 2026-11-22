package com.zyvo.universalprint.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyvo.universalprint.data.db.entity.PrinterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrinterDao {
    @Query("SELECT * FROM printers")
    fun getAll(): Flow<List<PrinterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PrinterEntity)

    @Delete
    suspend fun delete(entity: PrinterEntity)

    @Query("DELETE FROM printers WHERE id = :id")
    suspend fun deleteById(id: String)
}
