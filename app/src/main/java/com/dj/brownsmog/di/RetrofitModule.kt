package com.dj.brownsmog.di

import com.dj.brownsmog.network.IQ_AIR_BASE_URL
import com.dj.brownsmog.network.OPEN_API_BASE_URL
import com.dj.brownsmog.network.IqAirRetrofitService
import com.dj.brownsmog.network.OpenApiRetrofitService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .connectTimeout(5000L, TimeUnit.SECONDS)
            .readTimeout(5000L, TimeUnit.SECONDS)
            .writeTimeout(5000L, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitModule(moshi: Moshi, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

    }

    @Singleton
    @Provides
    fun provideOpenApiService(retrofit: Retrofit.Builder): OpenApiRetrofitService {
        return retrofit.baseUrl(OPEN_API_BASE_URL).build().create(OpenApiRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideIqAirApiService(retrofit: Retrofit.Builder): IqAirRetrofitService {
        return retrofit.baseUrl(IQ_AIR_BASE_URL).build().create(IqAirRetrofitService::class.java)
    }
}