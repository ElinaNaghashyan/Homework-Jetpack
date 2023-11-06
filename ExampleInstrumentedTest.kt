package com.example.myapplication444

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class City(val name: String, val description: String, val imageResId: Int)

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Display a welcoming message
        Text(
            text = "Welcome to the City Explorer",
            style = MaterialTheme.typography.h4
        )

        // Create a button for navigation to the second screen
        Button(
            onClick = { navController.navigate("second_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Explore Cities")
        }
    }
}

@Composable
fun SecondScreen(
    city: City,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display a descriptive section about the city
        Text(
            text = "City: ${city.name}",
            style = MaterialTheme.typography.h4
        )

        Text(
            text = "Description: ${city.description}",
            style = MaterialTheme.typography.body1
        )

        // Include an image relevant to the city
        Image(
            painter = painterResource(id = city.imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        // Add a back button to return to the welcome screen
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Back")
        }
    }
}

@Composable
fun CityListScreen(
    cities: List<City>,
    onCitySelected: (City) -> Unit
) {
    Column {
        // Display a list of cities
        LazyColumn {
            items(cities) { city ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onCitySelected(city) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(text = city.name, style = MaterialTheme.typography.h5)
                        Text(text = city.description, style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}

@Composable
fun CityExplorerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome_screen"
    ) {
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("second_screen/{cityName}/{cityDescription}/{cityImageResId}") { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName")
            val cityDescription = backStackEntry.arguments?.getString("cityDescription")
            val cityImageResId = backStackEntry.arguments?.getInt("cityImageResId")
            SecondScreen(
                city = City(cityName ?: "", cityDescription ?: "", cityImageResId ?: 0),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    CityExplorerApp()
}

@Composable
fun AppContent() {
    val cities = listOf(
        City("Yerevan", "The capital of Armenia", R.drawable.yerevan),
        City("Washington", "The capital of the United States", R.drawable.washington),
        City("Madrid", "The capital of Spain", R.drawable.madrid)
    )

    val navController = rememberNavController()

    CityExplorerApp()
}

@Preview(showBackground = true)
@Composable
fun PreviewAppContent() {
    AppContent()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

// For some tasks I have used chatGPT