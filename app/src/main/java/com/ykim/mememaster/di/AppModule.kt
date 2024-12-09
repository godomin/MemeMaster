package com.ykim.mememaster.di

import android.app.Application
import androidx.room.Room
import com.ykim.mememaster.data.HistoryManagerImpl
import com.ykim.mememaster.data.ImageRepositoryImpl
import com.ykim.mememaster.data.MemeRepositoryImpl
import com.ykim.mememaster.data.database.MemeDatabase
import com.ykim.mememaster.domain.HistoryManager
import com.ykim.mememaster.domain.ImageRepository
import com.ykim.mememaster.domain.MemeRepository
import com.ykim.mememaster.presentation.model.OverlayText
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    fun bindMemeRepository(memeRepositoryImpl: MemeRepositoryImpl): MemeRepository

    companion object {
        @Provides
        @Singleton
        fun provideHistoryManager(): HistoryManager<List<OverlayText>> {
            return HistoryManagerImpl(6)    // initial state + max redo state (5)
        }

        @Provides
        @Singleton
        fun provideMemeDatabase(app: Application): MemeDatabase {
            return Room.databaseBuilder(
                app,
                MemeDatabase::class.java,
                MemeDatabase.DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideMemeDao(memeDatabase: MemeDatabase) = memeDatabase.dao
    }
}