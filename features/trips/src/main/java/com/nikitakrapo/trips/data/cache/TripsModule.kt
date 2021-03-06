package com.nikitakrapo.trips.data.cache

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TripsModule {

    @Provides
    @Singleton
    fun provideTripsDatabase(context: Context): TripsDatabase {
        return Room.databaseBuilder(
            context,
            TripsDatabase::class.java,
            TripsDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserTripsDao(tripsDatabase: TripsDatabase): UserTripsDao {
        return tripsDatabase.userTripsDao()
    }
}