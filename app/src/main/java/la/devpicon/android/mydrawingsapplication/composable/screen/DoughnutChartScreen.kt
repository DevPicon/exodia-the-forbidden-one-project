package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.draw.DoughnutChart
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import kotlin.math.roundToInt

@Composable
fun DoughnutChartScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        var sliderPosition by remember { mutableFloatStateOf(0.5f) }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Team A: ${(sliderPosition.times(100)).roundToInt()}%")
            Text("Team B: ${((1f.minus(sliderPosition)).times(100).roundToInt())}%")
        }

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..1f,
            steps = 100

        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            DoughnutChart(
                percentageA = sliderPosition,
                percentageB = (1f - sliderPosition)
            )
        }
    }
}

@Preview
@Composable
private fun DoughnutChartScreenPreview() {
    MyDrawingsApplicationTheme {
        Surface() {
            DoughnutChartScreen()
        }
    }
}