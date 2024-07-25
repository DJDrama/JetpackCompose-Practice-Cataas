package com.dj.android.catassjetpackcompose

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.dj.android.catassjetpackcompose.data.workers.PetsSyncWorker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PetsSyncWorkerTest {
    @get:Rule
    val koinTestRule = KoinTestRule()

    @Before
    fun setUp() {
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(loggingLevel = Log.DEBUG)
            .setExecutor(executor = SynchronousExecutor())
            .build()

        // Initialize workManager for instrumentation tests
        WorkManagerTestInitHelper.initializeTestWorkManager(
            /* context = */ ApplicationProvider.getApplicationContext(),
            /* configuration = */ config
        )
    }

    @Test
    fun testPetsSyncWorker() {
        val syncPetsWorkRequest = OneTimeWorkRequestBuilder<PetsSyncWorker>()
            .setConstraints(
                constraints = Constraints.Builder()
                    .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(requiresBatteryNotLow = true)
                    .build()
            ).build()

        val workManager = WorkManager.getInstance(ApplicationProvider.getApplicationContext())
        val testDriver =
            WorkManagerTestInitHelper.getTestDriver(ApplicationProvider.getApplicationContext())!!

        workManager.enqueueUniqueWork(
            "PetsSyncWorker",
            ExistingWorkPolicy.KEEP,
            syncPetsWorkRequest
        ).result.get()

        val workInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()

        // assert that our work is enqueued
        assert(WorkInfo.State.ENQUEUED == workInfo.state)

        // simulate our constraints being met by using the testDriver instance that we created earlier
        testDriver.setAllConstraintsMet(syncPetsWorkRequest.id)


        val postRequirementWorkInfo = workManager.getWorkInfoById(syncPetsWorkRequest.id).get()
        // check if work is running.
        assert(WorkInfo.State.RUNNING == postRequirementWorkInfo.state)
    }
}