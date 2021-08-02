package de.westwing.campaignbrowser.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.westwing.campaignbrowser.R
import de.westwing.campaignbrowser.service.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    companion object {
        @Provides
        @Named("baseUrl")
        fun baseUrl(@ApplicationContext context: Context): String {
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
                .client(okHttpClient())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}