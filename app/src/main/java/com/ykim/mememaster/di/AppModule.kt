package com.ykim.mememaster.di

import android.content.Context
import com.ykim.mememaster.data.HistoryManagerImpl
import com.ykim.mememaster.data.ImageRepositoryImpl
import com.ykim.mememaster.domain.HistoryManager
import com.ykim.mememaster.domain.ImageRepository
import com.ykim.mememaster.presentation.model.OverlayText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideHistoryManager(): HistoryManager<List<OverlayText>> {
            return HistoryManagerImpl(6)    // initial state + max redo state (5)
        }

        @Provides
        @Singleton
        fun provideImageRepository(
            @ApplicationContext context: Context
        ): ImageRepository {
            return ImageRepositoryImpl(context)
        }
    }
}