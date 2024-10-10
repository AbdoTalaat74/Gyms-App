package com.example.gymsapp.gyms.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymsapp.composables.DefaultIcon
import com.example.gymsapp.composables.GymDetails
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun GymDetailsScreen(modifier: Modifier = Modifier) {
    val viewModel: GymDetailsViewModel = hiltViewModel()

    val item = viewModel.state.value

    item?.let {gym ->

        Scaffold {
            Column(
                modifier = Modifier.padding()
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultIcon(
                    icon = Icons.Filled.LocationOn,
                    modifier = Modifier.padding(top = 32.dp, bottom = 32.dp),
                    contentDescription = "Location Icon"
                )
                GymDetails(
                    gym = gym,
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )

                Text(
                    text = if (gym.isOpen) "Gym is open " else "Gym is closed",
                    color = if (gym.isOpen) Color.Green else Color.Red
                )
            }
        }


    }
}