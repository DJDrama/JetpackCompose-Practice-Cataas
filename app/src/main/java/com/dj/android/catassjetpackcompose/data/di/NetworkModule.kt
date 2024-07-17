package com.dj.android.catassjetpackcompose.data.di

import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
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
            .build()
    }
    single {
        get<Retrofit>().create(CatsAPI::class.java)
    }
}