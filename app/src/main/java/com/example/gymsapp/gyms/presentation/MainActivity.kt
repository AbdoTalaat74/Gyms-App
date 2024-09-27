package com.example.gymsapp.gyms.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymsapp.gyms.presentation.details.GymDetailsScreen
import com.example.gymsapp.gyms.presentation.gymsList.GymsScreen
import com.example.gymsapp.gyms.presentation.gymsList.GymsViewModel
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
            val viewModel: GymsViewModel = viewModel()
            GymsScreen(
                state = viewModel.state.value,
                onItemClick = { id -> navController.navigate("gyms/$id") },
                onFavoriteIconClick = {id,oldValue ->
                    viewModel.toggleFavoriteState(id,oldValue)
                }
            )
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
