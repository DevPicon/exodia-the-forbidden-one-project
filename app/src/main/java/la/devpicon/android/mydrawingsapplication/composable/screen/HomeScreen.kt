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

data class HomeActions(
    val onOpenBasicDrawing: () -> Unit,
    val onOpenDoughnutChart: () -> Unit,
    val onOpenWorkoutTimer: () -> Unit,
    val onOpenScratchCard: () -> Unit,
    val onOpenStatComparison: () -> Unit
)

@Composable
fun HomeScreen(
    actions: HomeActions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)

        Button(
            onClick = actions.onOpenBasicDrawing,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_basic_sample))
        }

        Button(
            onClick = actions.onOpenDoughnutChart,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_possesion_doughnut_chart))
        }

        Button(
            onClick = actions.onOpenWorkoutTimer,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_workout_timer))
        }

        Button(
            onClick = actions.onOpenScratchCard,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_scratch_card))
        }

        Button(
            onClick = actions.onOpenStatComparison,
            modifier = buttonModifier
        ) {
            Text(stringResource(R.string.label_stat_comparison))
        }
    }
}

@Preview
@Composable
private fun MenuPreview() {
    MyDrawingsApplicationTheme {
        HomeScreen(
            actions = HomeActions(
                onOpenBasicDrawing = {},
                onOpenDoughnutChart = {},
                onOpenWorkoutTimer = {},
                onOpenScratchCard = {},
                onOpenStatComparison = {}
            )
        )
    }
}
