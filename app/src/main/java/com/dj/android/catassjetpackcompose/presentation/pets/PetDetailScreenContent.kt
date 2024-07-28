@file:OptIn(ExperimentalLayoutApi::class)

package com.dj.android.catassjetpackcompose.presentation.pets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dj.android.catassjetpackcompose.data.model.Cat

@Composable
fun PetDetailScreenContent(
    modifier: Modifier,
    cat: Cat,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = "https://cataas.com/cat/${cat.id}",
            contentDescription = "Cute cat",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            contentScale = ContentScale.FillWidth,
        )
        FlowRow(
            modifier =
                Modifier
                    .padding(horizontal = 6.dp),
        ) {
            repeat(cat.tags.size) {
                SuggestionChip(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    onClick = { /*TODO*/ },
                    label = {
                        Text(text = cat.tags[it])
                    },
                )
            }
        }
    }
}