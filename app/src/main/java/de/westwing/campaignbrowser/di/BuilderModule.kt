package de.westwing.campaignbrowser.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import de.westwing.campaignbrowser.presentation.list.CampaignListActivity

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [CampaignListActivityModule::class])
    abstract fun bindCampaignListActivity(): CampaignListActivity
}


@Module
class CampaignListActivityModule