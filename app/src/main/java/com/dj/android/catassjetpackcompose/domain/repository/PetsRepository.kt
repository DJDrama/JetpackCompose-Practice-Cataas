package com.dj.android.catassjetpackcompose.domain.repository

import com.dj.android.catassjetpackcompose.data.model.Cat
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    fun getPets(): Flow<List<Cat>>

    suspend fun fetchRemoteCats()

    suspend fun updateCat(cat: Cat)

    fun getFavoritePets(): Flow<List<Cat>>
}