@file:OptIn(ExperimentalMaterial3Api::class)

package com.dj.android.catassjetpackcompose.presentation.pets

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dj.android.catassjetpackcompose.R
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.presentation.navigation.ContentType
import com.dj.android.catassjetpackcompose.presentation.utils.permission.PermissionAction
import com.dj.android.catassjetpackcompose.presentation.utils.permission.PermissionDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetsScreen(
    onPetClicked: (Cat) -> Unit,
    contentType: ContentType,
    viewModel: PetsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var showContent: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    PermissionDialog(context = context, permission = Manifest.permission.ACCESS_COARSE_LOCATION) {
        when (it) {
            PermissionAction.PermissionDenied -> showContent = false
            PermissionAction.PermissionGranted -> {
                showContent = true
                Toast.makeText(
                    context,
                    context.getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
    if (showContent) {
        PetsScreenContent(
            modifier =
                Modifier
                    .fillMaxSize(),
            onPetClicked = onPetClicked,
            contentType = contentType,
            uiState = uiState,
            onFavoriteClicked = { cat ->
                viewModel.updateCat(cat = cat)
            },
        )
    }
}