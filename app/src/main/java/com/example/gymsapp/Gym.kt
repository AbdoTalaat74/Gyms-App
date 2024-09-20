package com.example.gymsapp

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector

val lisOfGyms = listOf(
    Gym(1,
        "UpTown Gym",
        "20 El-Gihad, Mit Akaba, Agouza, Giza Governorate 3754204, Egypt",
        Icons.Default.Place
    ),
    Gym(2,
        "Gold's Gym",
        "61 Syria, Mit Akaba, Agouza, Giza Governorate 3753202, Egypt",
        Icons.Default.Place
    ),
    Gym(3,
        "Hammer Gym",
        "7 Sphinx Square, Agouza, Giza Governorate 3753612, Egypt",
        Icons.Default.Place
    ),
    Gym(4,
        "I-Energy Gym",
        "22 Gool Gamal, Al Huwaiteyah, Agouza, Giza Governorate 3753620, Egypt",
        Icons.Default.Place
    ),
    Gym(5,
        "H2O Gym & Spa",
        "32 Anas Ibn Malek, Mit Akaba, Agouza, Giza Governorate 3752212, Egypt",
        Icons.Default.Place
    )
)

data class Gym(
    val id:Int,
    val name: String,
    val location: String,
    val img: ImageVector,
    var isFavorite:Boolean = false
)