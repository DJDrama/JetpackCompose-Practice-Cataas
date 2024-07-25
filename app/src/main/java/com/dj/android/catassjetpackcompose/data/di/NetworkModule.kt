package com.dj.android.catassjetpackcompose.data.di

import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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