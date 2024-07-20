@file:OptIn(ExperimentalMaterial3Api::class)

package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.presentation.navigation.ContentType
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetsScreen(
    onPetClicked: (Cat) -> Unit,
    contentType: ContentType,
    viewModel: PetsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PetsScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        onPetClicked = onPetClicked,
        contentType = contentType,
        uiState = uiState
    )
}