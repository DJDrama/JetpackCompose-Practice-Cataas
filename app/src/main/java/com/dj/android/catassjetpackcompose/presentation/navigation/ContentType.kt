package com.dj.android.catassjetpackcompose.presentation.navigation

sealed interface ContentType {
    data object List: ContentType
    data object ListAndDetail: ContentType
}