package com.dj.android.catassjetpackcompose.data.repository

import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import com.dj.android.catassjetpackcompose.domain.utils.Result

class PetsRepositoryImpl(
    private val catsAPI: CatsAPI
) : PetsRepository {
    override suspend fun getPets(): Result<List<Cat>> {
        return try {
            val response = catsAPI.fetchCats(tag = "cute")
            if (response.isSuccessful) {
                Result.Success(data = response.body() ?: emptyList())
            } else {
                Result.Error(error = response.errorBody().toString())
            }
        } catch (e: Exception) {
            Result.Error(error = e.message ?: "Unknown Error")
        }
    }
}