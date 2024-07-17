package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dj.android.catassjetpackcompose.presentation.pets.components.PetListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetsScreen(
    modifier: Modifier = Modifier,
    viewModel: PetsViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = uiState.isLoading) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = uiState.pets.isNotEmpty()) {
            LazyColumn {
                items(items = uiState.pets) { pet ->
                    PetListItem(cat = pet)
                }
            }
        }
        AnimatedVisibility(visible = uiState.error != null) {
            Text(text = uiState.error ?: "")
        }
    }
}