package com.chkan.clientserverappbest.di

import android.content.Context
import com.chkan.clientserverappbest.BuildConfig
import com.chkan.clientserverappbest.data.network.AuthInterceptor
import com.chkan.clientserverappbest.data.network.MainService
import com.chkan.clientserverappbest.data.network.SearchService
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
import javax.inject.Named
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
    @Named("Main")
    internal fun provideRetrofit(BASE_URL : String, @Named("Main") okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    internal fun provideMainService(@Named("Main") retrofit : Retrofit) : MainService = retrofit.create(
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
    @Named("Main")
    internal fun mainHttpClient(
        chuck: ChuckerInterceptor,
        requestInterceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(chuck)
            .addInterceptor(requestInterceptor)
        return builder.build()
    }

    @Provides
    @Named("Search")
    internal fun searchHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor("220796fbb4634c2a95ef398e72e98ae5"))
            .build()
    }

    @Provides
    @Singleton
    @Named("Search")
    internal fun provideSearchRetrofit(@Named("Search") okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://newsapi.org")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    internal fun provideSearchService(@Named("Search") retrofit : Retrofit) : SearchService = retrofit.create(
        SearchService::class.java)

}