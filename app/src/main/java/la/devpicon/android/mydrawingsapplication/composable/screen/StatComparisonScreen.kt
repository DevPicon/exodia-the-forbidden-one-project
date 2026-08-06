package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.draw.StatComparison
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import kotlin.math.roundToInt

internal const val BLUE_TEAM_SLIDER_TEST_TAG = "blueTeamSlider"
internal const val RED_TEAM_SLIDER_TEST_TAG = "redTeamSlider"

@Composable
fun StatComparisonScreen(modifier: Modifier = Modifier) {
    var blueTeamValue by rememberSaveable { mutableIntStateOf(50) }
    var redTeamValue by rememberSaveable { mutableIntStateOf(50) }

    StatComparisonContent(
        blueTeamValue = blueTeamValue,
        redTeamValue = redTeamValue,
        onBlueTeamValueChange = { blueTeamValue = it },
        onRedTeamValueChange = { redTeamValue = it },
        modifier = modifier
    )
}

@Composable
internal fun StatComparisonContent(
    blueTeamValue: Int,
    redTeamValue: Int,
    onBlueTeamValueChange: (Int) -> Unit,
    onRedTeamValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.stat_comparison_instruction),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )

        TeamValueControl(
            label = stringResource(R.string.stat_comparison_blue_value, blueTeamValue),
            value = blueTeamValue,
            onValueChange = onBlueTeamValueChange,
            modifier = Modifier.fillMaxWidth(),
            testTag = BLUE_TEAM_SLIDER_TEST_TAG
        )

        TeamValueControl(
            label = stringResource(R.string.stat_comparison_red_value, redTeamValue),
            value = redTeamValue,
            onValueChange = onRedTeamValueChange,
            modifier = Modifier.fillMaxWidth(),
            testTag = RED_TEAM_SLIDER_TEST_TAG
        )

        StatComparison(
            blueTeamValue = blueTeamValue,
            redTeamValue = redTeamValue,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (blueTeamValue == 0 && redTeamValue == 0) {
            Text(
                text = stringResource(R.string.stat_comparison_empty_values),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TeamValueControl(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = label)
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.roundToInt()) },
            modifier = Modifier.testTag(testTag),
            valueRange = 0f..100f,
            steps = 99
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StatComparisonScreenPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            StatComparisonContent(
                blueTeamValue = 65,
                redTeamValue = 35,
                onBlueTeamValueChange = {},
                onRedTeamValueChange = {}
            )
        }
    }
}
