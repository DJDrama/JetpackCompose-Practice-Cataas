package com.dj.android.catassjetpackcompose.domain.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val error: String) : Result<Nothing>()
}