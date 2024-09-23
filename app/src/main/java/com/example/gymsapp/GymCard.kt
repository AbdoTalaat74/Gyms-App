package com.example.gymsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp



@Composable
fun GymDetails(gym: Gym, modifier: Modifier, horizontalAlignment: Alignment.Horizontal = Alignment.Start) {
    Column(
        modifier = modifier
            .padding(8.dp)
        , horizontalAlignment = horizontalAlignment
    ) {
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
@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick() },
        colorFilter = ColorFilter.tint(Color.DarkGray)
    )
}


@Composable
fun GymItem(gym:Gym, onFavoriteIconClick:(Int)->Unit, onItemClick:(Int)->Unit){
    val icon = if (gym.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(gym.id) }
        ,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.Start
        ) {
            DefaultIcon(
                icon = Icons.Default.Place,
                contentDescription = "Location Icon",
                modifier = Modifier
                    .weight(0.15f)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),

                )
            GymDetails(gym, Modifier.weight(0.7f))

            DefaultIcon(
                icon,
                contentDescription = "Favorite Gym Icon",
                modifier = Modifier
                    .weight(0.15f)
                    .align(Alignment.CenterVertically)
            ) {
                onFavoriteIconClick(gym.id)
            }
        }
    }
}