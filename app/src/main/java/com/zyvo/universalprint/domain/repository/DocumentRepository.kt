package com.zyvo.universalprint.domain.repository

import com.zyvo.universalprint.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun observeAll(): Flow<List<Document>>
    suspend fun getById(id: String): Document?
    suspend fun save(document: Document)
    suspend fun delete(id: String)
}
