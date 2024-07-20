@file:OptIn(ExperimentalLayoutApi::class)

package com.dj.android.catassjetpackcompose.presentation.pets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dj.android.catassjetpackcompose.data.model.Cat

@Composable
fun PetListItem(
    modifier: Modifier = Modifier,
    cat: Cat,
    onPetClicked: (Cat) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 6.dp),
        onClick = { onPetClicked(cat) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            AsyncImage(
                model = "https://cataas.com/cat/${cat.id}",
                contentDescription = "Cat Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 200.dp),
                contentScale = ContentScale.FillWidth
            )
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
        }
    }
}