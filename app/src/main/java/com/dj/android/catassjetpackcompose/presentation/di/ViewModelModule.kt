package com.dj.android.catassjetpackcompose.presentation.di

import com.dj.android.catassjetpackcompose.presentation.pets.PetsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule =
    module {
        viewModelOf(::PetsViewModel)
    }