package com.zyvo.universalprint.data.repository

import com.zyvo.universalprint.data.db.dao.PaperSizeDao
import com.zyvo.universalprint.data.mapper.toDomain
import com.zyvo.universalprint.data.mapper.toEntity
import com.zyvo.universalprint.domain.model.PaperSize
import com.zyvo.universalprint.domain.repository.PaperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaperRepositoryImpl @Inject constructor(
    private val dao: PaperSizeDao
) : PaperRepository {
    override fun observeCustom(): Flow<List<PaperSize>> =
        dao.getCustom().map { list -> list.map { it.toDomain() } }

    override suspend fun saveCustom(paper: PaperSize) {
        dao.insert(paper.toEntity())
    }

    override suspend fun deleteCustom(id: String) {
        dao.deleteById(id)
    }
}
