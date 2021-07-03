package de.westwing.campaignbrowser.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.service.ApiInterface
import de.westwing.campaignbrowser.repository.CampaignRepositoryImpl
import de.westwing.campaignbrowser.repository.CampaignRepository
import de.westwing.campaignbrowser.CampaignApp
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(app: CampaignApp): Context

    @Binds
    abstract fun bindCampaignRepository(campaignRepositoryImpl: CampaignRepositoryImpl): CampaignRepository

    companion object {

        @Provides
        @Named("baseUrl")
        fun baseUrl(context: Context): String {
            return context.getString(R.string.base_url)
        }

        @Provides
        @Singleton
        fun okHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient.Builder()
            builder.connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                //.retryOnConnectionFailure(true)
            return builder.build()
        }

        @Provides
        @Singleton
        fun moshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        fun provideApiInterface(
            @Named("baseUrl") baseUrl: String,
            moshi: Moshi
        ): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }

        @Provides
        @Named("IO_SCHEDULER")
        fun provideIoScheduler(): Scheduler = Schedulers.io()
    }
}