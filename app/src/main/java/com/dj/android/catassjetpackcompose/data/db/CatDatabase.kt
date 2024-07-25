package com.dj.android.catassjetpackcompose.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dj.android.catassjetpackcompose.data.db.converters.CatsTypeConverters
import com.dj.android.catassjetpackcompose.data.db.dao.CatDao
import com.dj.android.catassjetpackcompose.data.db.entity.CatEntity

@Database(
    entities = [CatEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(CatsTypeConverters::class)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}