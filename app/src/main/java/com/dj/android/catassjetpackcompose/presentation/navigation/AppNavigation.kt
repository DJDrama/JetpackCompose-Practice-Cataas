package com.dj.android.catassjetpackcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dj.android.catassjetpackcompose.presentation.pets.FavoritePetsScreen
import com.dj.android.catassjetpackcompose.presentation.pets.PetDetailsScreen
import com.dj.android.catassjetpackcompose.presentation.pets.PetsScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.PetsScreen.route
    ) {
        composable(
            route = Screens.PetsScreen.route,
        ) {
            PetsScreen(
                onPetClicked = { cat ->
                    navController.navigate(
                        "${Screens.PetDetailScreen.route}/${Json.encodeToString(cat)}"
                    )
                },
                contentType = contentType
            )
        }
        composable(route = Screens.PetDetailScreen.route + "/{cat}",
            arguments = listOf(
                navArgument("cat") {
                    type = NavType.StringType
                }
            )) {
            PetDetailsScreen(cat = Json.decodeFromString(it.arguments?.getString("cat") ?: "")) {
                navController.navigateUp()
            }
        }

        composable(Screens.FavoritePetsScreen.route) {
            FavoritePetsScreen()
        }
    }
}