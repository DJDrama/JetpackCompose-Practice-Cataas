package com.dj.android.catassjetpackcompose.presentation.navigation

sealed class Screens(val route: String) {
    data object PetsScreen: Screens("Pets")
    data object PetDetailScreen: Screens("petDetails")
    data object FavoritePetsScreen: Screens("favoritePets")
}