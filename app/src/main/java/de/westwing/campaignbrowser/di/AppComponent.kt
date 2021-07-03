package de.westwing.campaignbrowser.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import de.westwing.campaignbrowser.CampaignApp
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, BuilderModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: CampaignApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: CampaignApp)
}
