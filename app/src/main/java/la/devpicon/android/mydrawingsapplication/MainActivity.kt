@file:OptIn(ExperimentalMaterial3Api::class)

package la.devpicon.android.mydrawingsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import la.devpicon.android.mydrawingsapplication.composable.Screens
import la.devpicon.android.mydrawingsapplication.composable.screen.CanvasComposableScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.DoughnutChartScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.HomeScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.WorkoutPauseTimerScreen
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDrawingsApplicationTheme {

                val navController = rememberNavController()


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val currentDestination =
                            navController.currentBackStackEntryAsState().value?.destination
                        TopAppBar(
                            title = {
                                Text(text = "My Drawings")
                            },
                            navigationIcon = {
                                if (currentDestination?.route != Screens.Home.router) {

                                    IconButton(
                                        onClick = {
                                            navController.navigateUp()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.router,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screens.Home.router) {
                            HomeScreen(
                                onNavigateToDoughnutChartScreen = {
                                    navController.navigate(Screens.Doughnut.router)
                                },
                                onNavigateToWorkoutPauseScreen = {
                                    navController.navigate(Screens.Workout.router)
                                },
                                onNavigateToBasicDrawScreen = {
                                    navController.navigate(Screens.Basic.router)
                                }
                            )
                        }
                        composable(route = Screens.Doughnut.router) {
                            DoughnutChartScreen()
                        }
                        composable(route = Screens.Workout.router) {
                            WorkoutPauseTimerScreen()
                        }
                        composable(route = Screens.Basic.router) {
                            CanvasComposableScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyDrawingsApplicationTheme {
        Greeting("Android")
    }
}