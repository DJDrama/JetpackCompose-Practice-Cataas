package com.dj.android.catassjetpackcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dj.android.catassjetpackcompose.R
import com.dj.android.catassjetpackcompose.presentation.pets.PetsScreen
import com.dj.android.catassjetpackcompose.presentation.ui.theme.CatassJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatassJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = stringResource(id = R.string.pets)
                            )
                        })
                    }, content = { paddingValues ->
                        PetsScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        )
                    })
            }
        }
    }
}