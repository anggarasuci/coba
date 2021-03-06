package com.example.coba.di

import android.content.Context
import com.example.coba.BuildConfig
import com.example.coba.data.model.ResponseBodyInterface
import com.example.coba.data.model.ResponseError
import com.example.coba.data.service.UserService
import com.example.coba.network.NetworkInterceptor
import com.example.coba.network.NetworkInterceptorCallback
import com.example.coba.network.RequestInterceptor
import com.example.coba.util.removeAllCache
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { createOkHttpClient(androidContext()) }
    single { createGson() }

    single { createApi<UserService>(get(), get()) }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    return OkHttpClient.Builder().apply {

        if (BuildConfig.DEBUG) {
            addInterceptor(httpLoggingInterceptor)
            addNetworkInterceptor(StethoInterceptor())
        }

        addInterceptor(RequestInterceptor())
        addInterceptor(NetworkInterceptor(context, object : NetworkInterceptorCallback {
            override fun onUnauthorized() {
                removeAllCache()
            }

            override fun getRequestExceptionMessage(): String {
                // show error service here
                return ""
            }

            override fun getRequestTimeoutMessage(): String {
                // show error timeout here
                return ""
            }

            override fun getNetworkUnavailableMessage(): String {
                // show error network here
                return ""
            }

            override fun getResponseBodyModel(): ResponseBodyInterface {
                // show message from backend
                return ResponseError()
            }
        }))
    }.build()
}

fun createGson(): Gson {
    val builder = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return builder.setLenient().create()
}

inline fun <reified T> createApi(okHttpClient: OkHttpClient, gson: Gson): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.APP_SERVICE_BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(T::class.java)
}
