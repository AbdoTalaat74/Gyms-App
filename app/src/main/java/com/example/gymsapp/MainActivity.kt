package com.example.gymsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymsapp.ui.theme.GymsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GymsAppTheme {
//                GymsScreen()
//                GymDetailsScreen()
                GymsAroundApp()

            }
        }
    }
}

@Composable
fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms_screen") {
        composable(route = "gyms_screen") {
            GymsScreen() { id ->
                navController.navigate("gym_details/$id")

            }
        }
        composable(route = "gym_details/{gym_id}",
            arguments = listOf(
                navArgument("gym_id") {
            type = NavType.IntType
        }
            )
        ) {
            GymDetailsScreen()
        }
    }

}
