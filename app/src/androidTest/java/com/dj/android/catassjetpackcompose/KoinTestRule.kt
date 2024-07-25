package com.dj.android.catassjetpackcompose

import androidx.test.core.app.ApplicationProvider
import com.dj.android.catassjetpackcompose.data.di.databaseModule
import com.dj.android.catassjetpackcompose.data.di.networkModule
import com.dj.android.catassjetpackcompose.data.di.repositoryModule
import com.dj.android.catassjetpackcompose.domain.di.dispatcherModule
import com.dj.android.catassjetpackcompose.presentation.di.viewModelModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class KoinTestRule: TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object: Statement(){
            override fun evaluate() {
                stopKoin()
                startKoin {
                    androidLogger(Level.ERROR)
                    androidContext(ApplicationProvider.getApplicationContext())
                    modules(
                        networkModule,
                        repositoryModule,
                        viewModelModule,
                        dispatcherModule,
                        databaseModule
                    )
                }
            }

        }
    }

}