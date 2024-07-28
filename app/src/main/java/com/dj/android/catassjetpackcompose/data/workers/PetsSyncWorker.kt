package com.dj.android.catassjetpackcompose.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dj.android.catassjetpackcompose.domain.repository.PetsRepository
import java.io.IOException

class PetsSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val petsRepository: PetsRepository,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            petsRepository.fetchRemoteCats()
            Result.success()
        } catch (e: IOException) {
            Log.d("PetsSyncWorker", "Error fetching pets", e)
            Result.failure()
        }
    }
}