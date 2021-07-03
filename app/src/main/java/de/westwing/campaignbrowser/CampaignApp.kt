package de.westwing.campaignbrowser

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import de.westwing.campaignbrowser.di.AppComponent
import de.westwing.campaignbrowser.di.DaggerAppComponent
import javax.inject.Inject

class CampaignApp: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector
}