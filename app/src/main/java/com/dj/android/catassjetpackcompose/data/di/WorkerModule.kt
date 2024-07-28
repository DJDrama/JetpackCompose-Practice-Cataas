package com.dj.android.catassjetpackcompose.data.di

import com.dj.android.catassjetpackcompose.data.workers.PetsSyncWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule =
    module {
        worker { PetsSyncWorker(get(), get(), get()) }
    }