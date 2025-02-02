package com.example.gymsapp.gyms.presentation.gymsList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymsapp.composables.GymItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymsScreen(
    state: GymsScreenState,
    onFavoriteIconClick: (id: Int, oldValue: Boolean) -> Unit,
    onItemClick: (
        id: Int
    ) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = "Gyms App") },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp)
        )
    }) { paddingValues ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                ), contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(state.gyms) { gym ->
                    GymItem(
                        gym = gym,
                        onFavoriteIconClick = { id, oldValue ->
                            onFavoriteIconClick(id, oldValue)
                        },
                        onItemClick = {
                            onItemClick(it)
                        }
                    )

                }
            }
            if (state.isLoading) CircularProgressIndicator()
            state.error?.let {

                Text(text = it, textAlign = TextAlign.Center)

            }
        }
    }

}


