package com.example.gymsapp

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Gym(
    val name: String,
    val location: String,
    val img: ImageVector
)