@file:OptIn(ExperimentalLayoutApi::class)

package com.dj.android.catassjetpackcompose.presentation.pets.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dj.android.catassjetpackcompose.R
import com.dj.android.catassjetpackcompose.data.model.Cat

@Composable
fun PetListItem(
    modifier: Modifier = Modifier,
    cat: Cat,
    onPetClicked: (Cat) -> Unit,
    onFavoriteClicked: (Cat) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                    onPetClicked(cat)
                }
        ) {
            AsyncImage(
                model = "https://cataas.com/cat/${cat.id}",
                contentDescription = "Cat Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 200.dp),
                contentScale = ContentScale.FillWidth
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                FlowRow(
                    modifier = Modifier.padding(horizontal = 6.dp)
                ) {
                    repeat(times = cat.tags.size) {
                        SuggestionChip(
                            modifier = Modifier.padding(horizontal = 3.dp),
                            onClick = { /*TODO*/ }, label = {
                                Text(text = cat.tags[it])
                            })
                    }
                }
                Icon(
                    modifier = Modifier.clickable {
                        onFavoriteClicked(cat.copy(isFavorite = !cat.isFavorite))
                    },
                    imageVector = if (cat.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.favorite),
                    tint = if (cat.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}