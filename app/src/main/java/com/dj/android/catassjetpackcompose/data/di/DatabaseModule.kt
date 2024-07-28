package com.dj.android.catassjetpackcompose.data.di

import androidx.room.Room
import com.dj.android.catassjetpackcompose.data.db.CatDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule =
    module {
        single {
            Room.databaseBuilder(
                androidContext(),
                CatDatabase::class.java,
                "cat-database",
            ).build()
        }
        single {
            get<CatDatabase>().catDao()
        }
    }