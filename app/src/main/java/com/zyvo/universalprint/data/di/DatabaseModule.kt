package com.zyvo.universalprint.data.di

import android.content.Context
import androidx.room.Room
import com.zyvo.universalprint.data.db.UniversalPrintDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): UniversalPrintDatabase =
        Room.databaseBuilder(ctx, UniversalPrintDatabase::class.java, "universal_print.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePaperSizeDao(db: UniversalPrintDatabase) = db.paperSizeDao()

    @Provides
    fun provideDocumentDao(db: UniversalPrintDatabase) = db.documentDao()

    @Provides
    fun provideLayerDao(db: UniversalPrintDatabase) = db.layerDao()

    @Provides
    fun providePrinterDao(db: UniversalPrintDatabase) = db.printerDao()
}
