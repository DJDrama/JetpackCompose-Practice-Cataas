package com.dj.android.catassjetpackcompose.data.network

import com.dj.android.catassjetpackcompose.data.model.Cat
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface CatsAPI {
    @GET("cats")
    suspend fun fetchCats(
        @Query("tag") tag: String,
    ): Response<List<Cat>>
}