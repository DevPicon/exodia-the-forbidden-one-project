@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import la.devpicon.android.mydrawingsapplication.composable.Screens
import la.devpicon.android.mydrawingsapplication.composable.screen.BasicDrawingScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.DoughnutChartScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.HomeActions
import la.devpicon.android.mydrawingsapplication.composable.screen.HomeScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.ScratchCardScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.StatComparisonScreen
import la.devpicon.android.mydrawingsapplication.composable.screen.WorkoutTimerScreen
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
                        val title = when (currentDestination?.route) {
                            Screens.Home.router -> getString(R.string.app_name)
                            Screens.Doughnut.router -> getString(R.string.label_possesion_doughnut_chart)
                            Screens.Workout.router -> getString(R.string.label_workout_timer)
                            Screens.Basic.router -> getString(R.string.label_basic_sample)
                            Screens.ScratchCard.router -> getString(R.string.label_scratch_card)
                            Screens.StatComparison.router -> getString(R.string.label_stat_comparison)
                            else -> getString(R.string.app_name)
                        }
                        TopAppBar(
                            title = {
                                Text(text = title)
                            },
                            navigationIcon = if (currentDestination?.route != Screens.Home.router) {
                                {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                }
                            } else {
                                {}
                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavigation(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    private fun AppNavigation(
        navController: NavHostController,
        modifier: Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.router,
            modifier = modifier
        ) {
            composable(route = Screens.Home.router) {
                HomeScreen(
                    actions = HomeActions(
                        onOpenBasicDrawing = {
                            navController.navigate(Screens.Basic.router)
                        },
                        onOpenDoughnutChart = {
                            navController.navigate(Screens.Doughnut.router)
                        },
                        onOpenWorkoutTimer = {
                            navController.navigate(Screens.Workout.router)
                        },
                        onOpenScratchCard = {
                            navController.navigate(Screens.ScratchCard.router)
                        },
                        onOpenStatComparison = {
                            navController.navigate(Screens.StatComparison.router)
                        }
                    )
                )
            }
            composable(route = Screens.Doughnut.router) {
                DoughnutChartScreen()
            }
            composable(route = Screens.Workout.router) {
                WorkoutTimerScreen()
            }
            composable(route = Screens.Basic.router) {
                BasicDrawingScreen()
            }
            composable(route = Screens.ScratchCard.router) {
                ScratchCardScreen()
            }
            composable(route = Screens.StatComparison.router) {
                StatComparisonScreen()
            }
        }
    }
}
