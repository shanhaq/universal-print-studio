package com.zyvo.universalprint.domain.repository

import com.zyvo.universalprint.domain.model.PaperSize
import kotlinx.coroutines.flow.Flow

interface PaperRepository {
    fun observeCustom(): Flow<List<PaperSize>>
    suspend fun saveCustom(paper: PaperSize)
    suspend fun deleteCustom(id: String)
}
