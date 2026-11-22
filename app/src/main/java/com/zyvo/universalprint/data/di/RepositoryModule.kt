package com.zyvo.universalprint.data.di

import com.zyvo.universalprint.data.repository.DocumentRepositoryImpl
import com.zyvo.universalprint.data.repository.PaperRepositoryImpl
import com.zyvo.universalprint.data.repository.PrinterRepositoryImpl
import com.zyvo.universalprint.domain.repository.DocumentRepository
import com.zyvo.universalprint.domain.repository.PaperRepository
import com.zyvo.universalprint.domain.repository.PrinterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDocumentRepository(impl: DocumentRepositoryImpl): DocumentRepository

    @Binds
    @Singleton
    abstract fun bindPaperRepository(impl: PaperRepositoryImpl): PaperRepository

    @Binds
    @Singleton
    abstract fun bindPrinterRepository(impl: PrinterRepositoryImpl): PrinterRepository
}
