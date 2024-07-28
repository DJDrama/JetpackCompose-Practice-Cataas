package com.dj.android.catassjetpackcompose.presentation.utils.permission

sealed interface PermissionAction {
    data object PermissionGranted: PermissionAction
    data object PermissionDenied: PermissionAction
}