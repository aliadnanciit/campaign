package de.westwing.campaignbrowser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import de.westwing.campaignbrowser.test.DoYes
import de.westwing.campaignbrowser.test.Yes

@Module
@InstallIn(ActivityComponent::class)
abstract class  TestModule {
    @Binds
    abstract fun bindTest(doYes: DoYes): Yes
}