package com.zyvo.universalprint.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zyvo.universalprint.data.db.entity.PaperSizeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaperSizeDao {
    @Query("SELECT * FROM paper_sizes")
    fun getAll(): Flow<List<PaperSizeEntity>>

    @Query("SELECT * FROM paper_sizes WHERE isCustom = 1")
    fun getCustom(): Flow<List<PaperSizeEntity>>

    @Query("SELECT * FROM paper_sizes WHERE id = :id")
    suspend fun getById(id: String): PaperSizeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PaperSizeEntity)

    @Update
    suspend fun update(entity: PaperSizeEntity)

    @Delete
    suspend fun delete(entity: PaperSizeEntity)

    @Query("DELETE FROM paper_sizes WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE paper_sizes SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: String, favorite: Boolean)
}
