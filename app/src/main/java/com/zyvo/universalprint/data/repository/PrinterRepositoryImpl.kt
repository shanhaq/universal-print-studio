package com.zyvo.universalprint.data.repository

import com.zyvo.universalprint.data.db.dao.PrinterDao
import com.zyvo.universalprint.data.mapper.toDomain
import com.zyvo.universalprint.data.mapper.toEntity
import com.zyvo.universalprint.domain.model.Printer
import com.zyvo.universalprint.domain.repository.PrinterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrinterRepositoryImpl @Inject constructor(
    private val dao: PrinterDao
) : PrinterRepository {
    override fun observeAll(): Flow<List<Printer>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun save(printer: Printer) {
        dao.insert(printer.toEntity())
    }

    override suspend fun delete(id: String) {
        dao.deleteById(id)
    }
}
