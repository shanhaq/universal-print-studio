package com.zyvo.universalprint.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zyvo.universalprint.data.db.entity.LayerEntity

@Dao
interface LayerDao {
    @Query("SELECT * FROM layers WHERE documentId = :documentId ORDER BY zIndex ASC")
    suspend fun getByDocument(documentId: String): List<LayerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<LayerEntity>)

    @Delete
    suspend fun delete(entity: LayerEntity)

    @Query("DELETE FROM layers WHERE documentId = :documentId")
    suspend fun deleteByDocument(documentId: String)

    @Query("DELETE FROM layers WHERE id = :id")
    suspend fun deleteById(id: String)
}
