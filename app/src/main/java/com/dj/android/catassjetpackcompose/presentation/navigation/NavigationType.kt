package com.dj.android.catassjetpackcompose.presentation.navigation

sealed interface NavigationType {
    data object BottomNavigation : NavigationType
    data object NavigationDrawer : NavigationType
    data object NavigationRail : NavigationType
}