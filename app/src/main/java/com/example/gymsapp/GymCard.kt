package com.example.gymsapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GymCard(gym: Gym) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),

            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = gym.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFC77DE6) // Purple
                    )
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = gym.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
@Preview
fun GymCardPreview() {
    GymCard(
        gym = Gym(
            name = "UpTown Gym",
            location = "20 El-Gihad, Mit Akaba, Agouza, Giza Governorate 3754204, Egypt",
            img = Icons.Default.LocationOn
        )
    )
}