package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.presentation.pets.components.PetListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritePetsScreen(
    onPetClicked: (Cat) -> Unit,
    petsViewModel: PetsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        petsViewModel.getFavoritePets()
    }
    val uiState by petsViewModel.uiState.collectAsStateWithLifecycle()
    val pets = uiState.favorites
    if (pets.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite pets")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(pets) { pet ->
                PetListItem(
                    cat = pet,
                    onPetClicked = onPetClicked,
                    onFavoriteClicked = {
                        petsViewModel.updateCat(it)
                    }
                )
            }
        }
    }

}