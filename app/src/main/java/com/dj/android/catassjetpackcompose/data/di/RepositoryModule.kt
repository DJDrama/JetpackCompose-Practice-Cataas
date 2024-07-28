package com.dj.android.catassjetpackcompose.data.di

import com.dj.android.catassjetpackcompose.data.repository.PetsRepositoryImpl
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule =
    module {
        singleOf(::PetsRepositoryImpl).bind<PetsRepository>()
    }