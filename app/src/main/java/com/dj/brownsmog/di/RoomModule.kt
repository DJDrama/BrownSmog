package com.dj.brownsmog.di

import androidx.room.Room
import com.dj.brownsmog.BaseApplication
import com.dj.brownsmog.db.AppDatabase
import com.dj.brownsmog.db.AppDatabase.Companion.DATABASE_NAME
import com.dj.brownsmog.db.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(app: BaseApplication): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLocationDao(app: AppDatabase): LocationDao{
        return app.locationDao()
    }

}