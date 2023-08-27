package com.shegs.identityqr.di

import android.content.Context
import androidx.room.Room
import com.shegs.identityqr.data.AppDatabase
import com.shegs.identityqr.data.InformationDAO
import com.shegs.identityqr.repositories.InformationRepository
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .build()
    }

    @Provides
    fun provideInformationDAO(database: AppDatabase): InformationDAO {
        return database.informationDao()
    }

}