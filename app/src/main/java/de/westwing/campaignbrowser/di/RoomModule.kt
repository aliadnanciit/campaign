package de.westwing.campaignbrowser.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.westwing.campaignbrowser.database.AppDatabase
import de.westwing.campaignbrowser.database.CampaignDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "AppDatabase"
        ).build()
    }

    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): CampaignDao {
        return appDatabase.campaignDao()
    }
}