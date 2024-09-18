package com.example.gymsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.gymsapp.ui.theme.GymsAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val gymList = listOf(
            Gym(
                "UpTown Gym",
                "20 El-Gihad, Mit Akaba, Agouza, Giza Governorate 3754204, Egypt",
                Icons.Default.Place
            ),
            Gym(
                "Gold's Gym",
                "61 Syria, Mit Akaba, Agouza, Giza Governorate 3753202, Egypt",
                Icons.Default.Place
            ),
            Gym(
                "Hammer Gym",
                "7 Sphinx Square, Agouza, Giza Governorate 3753612, Egypt",
                Icons.Default.Place
            ),
            Gym(
                "I-Energy Gym",
                "22 Gool Gamal, Al Huwaiteyah, Agouza, Giza Governorate 3753620, Egypt",
                Icons.Default.Place
            ),
            Gym(
                "H2O Gym & Spa",
                "32 Anas Ibn Malek, Mit Akaba, Agouza, Giza Governorate 3752212, Egypt",
                Icons.Default.Place
            )
        )

        setContent {
            GymsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = { Text(text = "Gyms App") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 4.dp)
                    )
                }) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding()),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(gymList.size) {
                            GymCard(gym = gymList[it])
                        }
                    }
                }

            }
        }
    }
}

