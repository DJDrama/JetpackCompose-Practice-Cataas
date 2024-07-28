package com.dj.android.catassjetpackcompose

import android.app.Application
import com.dj.android.catassjetpackcompose.data.di.databaseModule
import com.dj.android.catassjetpackcompose.data.di.networkModule
import com.dj.android.catassjetpackcompose.data.di.repositoryModule
import com.dj.android.catassjetpackcompose.domain.di.dispatcherModule
import com.dj.android.catassjetpackcompose.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class CatassJetpackComposeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = applicationContext)
            workManagerFactory()
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                dispatcherModule,
                databaseModule,
            )
        }
    }
}