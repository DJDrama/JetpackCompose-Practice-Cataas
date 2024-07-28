package com.dj.android.catassjetpackcompose.data.repository

import com.dj.android.catassjetpackcompose.data.db.dao.CatDao
import com.dj.android.catassjetpackcompose.data.db.entity.CatEntity
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.data.network.CatsAPI
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import com.dj.android.catassjetpackcompose.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException

class PetsRepositoryImpl : PetsRepository {
    private val catsAPI: CatsAPI
    private val dispatcher: CoroutineDispatcher
    private val catDao: CatDao

    constructor(catsAPI: CatsAPI, dispatcher: CoroutineDispatcher, catDao: CatDao) {
        this.catsAPI = catsAPI
        this.dispatcher = dispatcher
        this.catDao = catDao
    }

    override fun getPets(): Flow<List<Cat>> {
        return catDao.getCats()
            .map {
                it.map { catEntity ->
                    Cat(
                        id = catEntity.id,
                        owner = catEntity.owner,
                        tags = catEntity.tags,
                        createdAt = catEntity.createdAt,
                        updatedAt = catEntity.updatedAt,
                    )
                }
            }
            .onEach {
                if (it.isEmpty()) {
                    fetchRemoteCats()
                }
            }
    }

    override suspend fun fetchRemoteCats() {
        try {
            val response = catsAPI.fetchCats(tag = "")
            if (response.isSuccessful) {
                response.body()!!.map {
                    catDao.insert(
                        CatEntity(
                            id = it.id,
                            owner = it.owner,
                            tags = it.tags,
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt,
                        ),
                    )
                }
            } else {
                error("Response is not successful")
            }
        } catch (e: IOException) {
            Result.Error(error = e.message ?: "Unknown Error")
        } catch(e: IllegalStateException){
            Result.Error(error = e.message ?: "Unknown Error")
        }
    }

    override suspend fun updateCat(cat: Cat) {
        catDao.update(
            catEntity =
                CatEntity(
                    id = cat.id,
                    owner = cat.owner,
                    tags = cat.tags,
                    createdAt = cat.createdAt,
                    updatedAt = cat.updatedAt,
                    isFavorite = cat.isFavorite,
                ),
        )
    }

    override fun getFavoritePets(): Flow<List<Cat>> {
        return catDao.getFavoriteCats().map { cached ->
            cached.map {
                Cat(
                    id = it.id,
                    owner = it.owner,
                    tags = it.tags,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt,
                    isFavorite = it.isFavorite,
                )
            }
        }
    }
}