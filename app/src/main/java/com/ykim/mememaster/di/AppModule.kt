package com.ykim.mememaster.di

import com.ykim.mememaster.data.HistoryManagerImpl
import com.ykim.mememaster.domain.HistoryManager
import com.ykim.mememaster.presentation.model.OverlayText
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    }
}