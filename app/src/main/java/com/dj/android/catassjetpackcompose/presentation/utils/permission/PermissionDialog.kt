package com.dj.android.catassjetpackcompose.presentation.utils.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dj.android.catassjetpackcompose.R

fun checkIfPermissionGranted(
    context: Context,
    permission: String,
): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission,
    ) == PackageManager.PERMISSION_GRANTED
}

fun shouldShowPermissionRationale(
    context: Context,
    permission: String,
): Boolean {
    val activity = context as Activity?
    if (activity == null) {
        Log.d("Permissions", "Activity is null")
    }

    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity!!,
        permission,
    )
}

@Composable
fun PermissionDialog(
    context: Context,
    permission: String,
    permissionAction: (PermissionAction) -> Unit,
) {
    val isPermissionGranted = checkIfPermissionGranted(context = context, permission = permission)
    if (isPermissionGranted) {
        permissionAction(PermissionAction.PermissionGranted)
        return
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                permissionAction(PermissionAction.PermissionGranted)
            } else {
                permissionAction(PermissionAction.PermissionDenied)
            }
        }

    val showPermissionRationale = shouldShowPermissionRationale(context, permission)
    var isDialogDismissed by remember { mutableStateOf(false) }
    var isPristine by remember { mutableStateOf(false) }

    if ((showPermissionRationale && !isDialogDismissed) || (!isDialogDismissed && !isPristine)) {
        isPristine = false
        AlertDialog(
            onDismissRequest = {
                isDialogDismissed = true
                permissionAction(PermissionAction.PermissionDenied)
            },
            title = { Text(text = stringResource(id = R.string.permission_required)) },
            text = {
                Text(text = stringResource(id = R.string.requires_location_permission))
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionLauncher.launch(permission)
                    },
                ) {
                    Text(text = stringResource(id = R.string.grant_access))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionAction(PermissionAction.PermissionDenied)
                    },
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
        )
    } else {
        if (!isDialogDismissed) {
            SideEffect {
                permissionLauncher.launch(permission)
            }
        }
    }
}