package com.zyvo.universalprint.data.repository

import com.zyvo.universalprint.data.db.dao.DocumentDao
import com.zyvo.universalprint.data.db.dao.LayerDao
import com.zyvo.universalprint.data.mapper.toDomain
import com.zyvo.universalprint.data.mapper.toEntity
import com.zyvo.universalprint.domain.model.Document
import com.zyvo.universalprint.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocumentRepositoryImpl @Inject constructor(
    private val documentDao: DocumentDao,
    private val layerDao: LayerDao
) : DocumentRepository {
    override fun observeAll(): Flow<List<Document>> =
        documentDao.getAll().map { list ->
            list.map { entity ->
                val layers = layerDao.getByDocument(entity.id)
                entity.toDomain(layers)
            }
        }

    override suspend fun getById(id: String): Document? {
        val entity = documentDao.getById(id) ?: return null
        val layers = layerDao.getByDocument(id)
        return entity.toDomain(layers)
    }

    override suspend fun save(document: Document) {
        documentDao.insert(document.toEntity())
        layerDao.deleteByDocument(document.id)
        layerDao.insertAll(document.layers.map { it.toEntity(document.id) })
    }

    override suspend fun delete(id: String) {
        documentDao.deleteById(id)
        layerDao.deleteByDocument(id)
    }
}
