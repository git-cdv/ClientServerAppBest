package com.chkan.clientserverappbest.data.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Dmytro Chkan on 13.12.2022.
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(API_KEY_HEADER, apiKey)
            .build()

        return chain.proceed(request)
    }

    private companion object {

        private const val API_KEY_HEADER = "X-Api-Key"
    }
}