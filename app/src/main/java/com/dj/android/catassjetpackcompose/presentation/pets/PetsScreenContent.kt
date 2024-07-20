package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dj.android.catassjetpackcompose.data.model.Cat
import com.dj.android.catassjetpackcompose.presentation.navigation.ContentType
import com.dj.android.catassjetpackcompose.presentation.pets.components.PetListItem

@Composable
fun PetsScreenContent(
    modifier: Modifier = Modifier,
    onPetClicked: (Cat) -> Unit,
    contentType: ContentType,
    uiState: PetsUiState,
) {

    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = uiState.isLoading) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(visible = uiState.pets.isNotEmpty()) {
            if (contentType == ContentType.List) {
                PetList(
                    onPetClicked = onPetClicked,
                    pets = uiState.pets,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                PetListAndDetails(
                    pets = uiState.pets
                )
            }
        }
        AnimatedVisibility(visible = uiState.error != null) {
            Text(text = uiState.error ?: "")
        }
    }
}

@Composable
fun PetList(modifier: Modifier = Modifier, onPetClicked: (Cat) -> Unit, pets: List<Cat>) {
    LazyColumn(modifier = modifier) {
        items(items = pets) { pet ->
            PetListItem(cat = pet, onPetClicked = onPetClicked)
        }
    }
}

@Composable
fun PetListAndDetails(pets: List<Cat>) {
    var currentPet by remember {
        mutableStateOf(pets.first())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PetList(
            onPetClicked = {
                currentPet = it
            },
            pets = pets,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        PetDetailsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            cat = currentPet
        )
    }
}