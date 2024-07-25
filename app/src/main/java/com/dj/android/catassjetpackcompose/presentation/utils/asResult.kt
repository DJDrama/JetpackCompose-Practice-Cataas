package com.dj.android.catassjetpackcompose.presentation.utils

import com.dj.android.catassjetpackcompose.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this.map<T, Result<T>> {
        Result.Success(it)
    }.catch {
        emit(Result.Error(it.message.toString()))
    }
}