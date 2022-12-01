package com.chkan.clientserverappbest.di

import android.content.Context
import com.chkan.clientserverappbest.BuildConfig
import com.chkan.clientserverappbest.data.network.MainService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val json by lazy {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    internal fun providesBaseUrl() : String = BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    internal fun provideRetrofit(BASE_URL : String, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    internal fun provideMainService(retrofit : Retrofit) : MainService = retrofit.create(
        MainService::class.java)

    @Provides
    internal fun chuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor(context)

    @Provides
    fun provideRequestInterceptor() = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        builder.header("app-id", "638901bdecd04b059b7b57de")
        return@Interceptor chain.proceed(builder.build())
    }

    @Provides
    internal fun okHttpClient(
        chuck: ChuckerInterceptor,
        requestInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(chuck)
            .addInterceptor(requestInterceptor)
        return builder.build()
    }
}