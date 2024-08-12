package com.dj.android.catassjetpackcompose

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dj.android.catassjetpackcompose.data.db.CatDatabase
import com.dj.android.catassjetpackcompose.data.db.dao.CatDao
import com.dj.android.catassjetpackcompose.data.db.entity.CatEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CatDaoTest {
    private lateinit var database: CatDatabase
    private lateinit var catDao: CatDao

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatDatabase::class.java
        ).allowMainThreadQueries().build()
        catDao = database.catDao()
    }

    @Test
    fun testInsertAndReadCat() = runTest {
        val cat = CatEntity(
            id = "1",
            owner = "John Doe",
            tags = listOf("cute", "fluffy"),
            createdAt = "2024-08-21T00:00:00.000Z",
            updatedAt = "2024-08-21T00:00:00.000Z",
            isFavorite = false
        )
        catDao.insert(cat)
        val cats = catDao.getCats()
        assert(cats.first().contains(cat))
    }

    @Test
    fun testAddCatToFavorites() = runTest {
        val cat = CatEntity(
            id = "1",
            owner = "John Doe",
            tags = listOf("cute", "fluffy"),
            createdAt = "2024-08-21T00:00:00.000Z",
            updatedAt = "2024-08-21T00:00:00.000Z",
            isFavorite = false
        )

        catDao.insert(cat)
        catDao.update(cat.copy(isFavorite = true))
        val favoriteCats = catDao.getFavoriteCats()
        assert(favoriteCats.first().contains(cat.copy(isFavorite = true)))
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}