package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDoughnutChartScreen: () -> Unit,
    onNavigateToWorkoutPauseScreen: () -> Unit,
    onNavigateToBasicDrawScreen: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val buttonModifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)

        Button(
            onClick = onNavigateToBasicDrawScreen,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_basic_sample))
        }

        Button(
            onClick = onNavigateToDoughnutChartScreen,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_possesion_doughnut_chart))
        }

        Button(
            onClick = onNavigateToWorkoutPauseScreen,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_workout_timer))
        }

        Button(
            onClick = {},
            modifier = buttonModifier
        ) {
            Text("Linea de comparación")
        }
    }
}

@Preview
@Composable
private fun MenuPreview() {
    MyDrawingsApplicationTheme {
        HomeScreen(
            onNavigateToDoughnutChartScreen = {},
            modifier = Modifier,
            onNavigateToWorkoutPauseScreen = {},
            onNavigateToBasicDrawScreen = {}
        )
    }
}