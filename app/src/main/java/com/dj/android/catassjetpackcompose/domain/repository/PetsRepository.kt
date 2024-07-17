package com.dj.android.catassjetpackcompose.domain.repository

import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.domain.utils.Result

interface PetsRepository {
    suspend fun getPets(): Result<List<Cat>>
}