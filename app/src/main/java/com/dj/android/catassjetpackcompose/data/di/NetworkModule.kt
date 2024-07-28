package com.dj.android.catassjetpackcompose.data.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        ChuckerInterceptor.Builder(androidContext())
                            .collector(ChuckerCollector(
                                context = androidContext(),
                                showNotification=true,
                                retentionPeriod = RetentionManager.Period.ONE_HOUR
                            ))
                            .maxContentLength(250_000L)
                            .redactHeaders(emptySet())
                            .alwaysReadResponseBody(false)
                            .build()
                    )
                    .addInterceptor(
                        HttpLoggingInterceptor().apply{
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .build()
            )
            .build()
    }
    single {
        get<Retrofit>().create(CatsAPI::class.java)
    }
}