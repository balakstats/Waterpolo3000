package com.example.waterpolo3000.di

import android.content.Context
import com.example.waterpolo3000.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideFunctionTypesDao(appDatabase: AppDatabase): FunctionTypesDao {
        return appDatabase.functionTypesDao()
    }

    @Provides
    fun provideGameEventsDao(appDatabase: AppDatabase): GameEventDao {
        return appDatabase.gameEventDao()
    }

    @Provides
    fun provideGameEventsTypesDao(appDatabase: AppDatabase): GameEventTypeDao {
        return appDatabase.gameEventTypeDao()
    }

    @Provides
    fun provideParticipantDao(appDatabase: AppDatabase): ParticipantDao {
        return appDatabase.participantDao()
    }
}