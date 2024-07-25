package com.dj.android.catassjetpackcompose.domain.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dispatcherModule = module {
    single { Dispatchers.IO }
}