package com.example.coba.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        return chain.proceed(
            builder
                //my public access token
                .addHeader("Authorization", "token 4b66132de70c3321f9305192b5d1ebf84f438958")
                .build()
        )
    }
}
