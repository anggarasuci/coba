package com.example.coba.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(
            builder
                //my public access token
                .addHeader("Authorization", "token 92f58b84343ceb8a108e8a1c331787c50cc46054")
                .build()
        )
    }
}
