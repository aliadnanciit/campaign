package de.westwing.campaignbrowser.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.westwing.campaignbrowser.repository.CampaignRepository
import de.westwing.campaignbrowser.repository.CampaignRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindCampaignRepository(campaignRepositoryImpl: CampaignRepositoryImpl): CampaignRepository



    companion object {
        @Provides
        @Named("IO_DISPATCHER")
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        @Named("DEFAULT_DISPATCHER")
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }
}