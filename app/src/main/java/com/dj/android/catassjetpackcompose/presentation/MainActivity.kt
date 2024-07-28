package com.dj.android.catassjetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dj.android.catassjetpackcompose.data.workers.PetsSyncWorker
import com.dj.android.catassjetpackcompose.presentation.navigation.AppNavigationContent
import com.dj.android.catassjetpackcompose.presentation.navigation.ContentType
import com.dj.android.catassjetpackcompose.presentation.navigation.DeviceFoldPosture
import com.dj.android.catassjetpackcompose.presentation.navigation.NavigationType
import com.dj.android.catassjetpackcompose.presentation.navigation.Screens
import com.dj.android.catassjetpackcompose.presentation.navigation.isBookPosture
import com.dj.android.catassjetpackcompose.presentation.navigation.isSeparating
import com.dj.android.catassjetpackcompose.presentation.theme.CatassJetpackComposeTheme
// import com.dj.android.catassjetpackcompose.test.LeakTestUtils
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startPetsSync()
        enableEdgeToEdge()

        // Testing Memory Leak
        /*AppWatcher.objectWatcher.expectWeaklyReachable(
            LeakTestUtils.leakCanaryTest,
            "Static reference to LeakCanaryTest"
        )*/

        val deviceFoldingPostureFlow =
            WindowInfoTracker
                .getOrCreate(this).windowLayoutInfo(this)
                .flowWithLifecycle(this.lifecycle)
                .map { layoutInfo ->
                    val foldingFeature =
                        layoutInfo.displayFeatures
                            .filterIsInstance<FoldingFeature>()
                            .firstOrNull()
                    when {
                        isBookPosture(foldingFeature) ->
                            DeviceFoldPosture.BookPosture(hingePosition = foldingFeature.bounds)

                        isSeparating(foldingFeature) ->
                            DeviceFoldPosture.SeparatingPosture(
                                hingePosition = foldingFeature.bounds,
                                orientation = foldingFeature.orientation,
                            )

                        else -> DeviceFoldPosture.NormalPosture
                    }
                }
                .stateIn(
                    scope = lifecycleScope,
                    started = SharingStarted.Eagerly,
                    initialValue = DeviceFoldPosture.NormalPosture,
                )
        setContent {
            val devicePosture = deviceFoldingPostureFlow.collectAsStateWithLifecycle().value
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController = rememberNavController()
            CatassJetpackComposeTheme {
                val navigationType: NavigationType
                val contentType: ContentType
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> {
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }

                    WindowWidthSizeClass.Medium -> {
                        navigationType = NavigationType.NavigationRail
                        contentType =
                            if (devicePosture is DeviceFoldPosture.BookPosture
                                || devicePosture is DeviceFoldPosture.SeparatingPosture) {
                                ContentType.ListAndDetail
                            } else {
                                ContentType.List
                            }
                    }

                    WindowWidthSizeClass.Expanded -> {
                        navigationType =
                            if (devicePosture is DeviceFoldPosture.BookPosture) {
                                NavigationType.NavigationRail
                            } else {
                                NavigationType.NavigationDrawer
                            }
                        contentType = ContentType.ListAndDetail
                    }

                    else -> {
                        navigationType = NavigationType.BottomNavigation
                        contentType = ContentType.List
                    }
                }
                if (navigationType == NavigationType.NavigationDrawer) {
                    PermanentNavigationDrawer(drawerContent = {
                        PermanentDrawerSheet {
                            PetsNavigationDrawer(
                                onFavoriteClicked = { navController.navigate(Screens.FavoritePetsScreen.route) },
                                onHomeClicked = { navController.navigate(Screens.PetsScreen.route) },
                            )
                        }
                    }) {
                        AppNavigationContent(
                            contentType = contentType,
                            navigationType = navigationType,
                            onFavoriteClicked = { navController.navigate(Screens.FavoritePetsScreen.route) },
                            onHomeClicked = { navController.navigate(Screens.PetsScreen.route) },
                            navHostController = navController,
                        )
                    }
                } else {
                    ModalNavigationDrawer(
                        drawerContent = {
                            ModalDrawerSheet {
                                PetsNavigationDrawer(
                                    onFavoriteClicked = { navController.navigate(Screens.FavoritePetsScreen.route) },
                                    onHomeClicked = { navController.navigate(Screens.PetsScreen.route) },
                                    onDrawerClicked = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    },
                                )
                            }
                        },
                        drawerState = drawerState,
                    ) {
                        AppNavigationContent(
                            contentType = contentType,
                            navigationType = navigationType,
                            onFavoriteClicked = { navController.navigate(Screens.FavoritePetsScreen.route) },
                            onHomeClicked = { navController.navigate(Screens.PetsScreen.route) },
                            navHostController = navController,
                            onDrawerClicked = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                        )
                    }
                }
            }
        }
    }

    // JUST FOR TESTING - should be in data layer
    private fun startPetsSync() {
        val syncPetsWorkRequest =
            OneTimeWorkRequestBuilder<PetsSyncWorker>()
                .setConstraints(
                    constraints =
                        Constraints.Builder()
                            .setRequiredNetworkType(networkType = NetworkType.CONNECTED)
                            .setRequiresBatteryNotLow(requiresBatteryNotLow = true)
                            .build(),
                )
                .build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(
                // uniqueWorkName =
                "PetsSyncWorker",
                // existingWorkPolicy =
                ExistingWorkPolicy.KEEP,
                // work =
                syncPetsWorkRequest,
            )
    }
}