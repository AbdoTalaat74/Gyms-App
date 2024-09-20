package com.example.gymsapp

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymScreen(modifier: Modifier = Modifier) {
    val vm:GymsViewModel = viewModel()

    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "Gyms App") },
            modifier = modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp)
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(vm.state){gym ->
                GymItem(gym) {
                    vm.toggleFavoriteState(it)

                }
            }
        }
    }
}