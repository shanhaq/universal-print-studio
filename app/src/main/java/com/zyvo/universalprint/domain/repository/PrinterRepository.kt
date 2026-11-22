package com.zyvo.universalprint.domain.repository

import com.zyvo.universalprint.domain.model.Printer
import kotlinx.coroutines.flow.Flow

interface PrinterRepository {
    fun observeAll(): Flow<List<Printer>>
    suspend fun save(printer: Printer)
    suspend fun delete(id: String)
}
