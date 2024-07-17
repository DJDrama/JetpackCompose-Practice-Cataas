package com.dj.android.catassjetpackcompose

import android.app.Application
import com.dj.android.catassjetpackcompose.data.di.networkModule
import com.dj.android.catassjetpackcompose.data.di.repositoryModule
import com.dj.android.catassjetpackcompose.presentation.di.viewModelModule
import org.koin.core.context.startKoin

class CatassJetpackComposeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}