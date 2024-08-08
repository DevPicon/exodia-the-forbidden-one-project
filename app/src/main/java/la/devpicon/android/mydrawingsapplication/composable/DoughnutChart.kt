package la.devpicon.android.mydrawingsapplication.composable

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun DoughnutChart(
    modifier: Modifier = Modifier,
    chartSize: Dp = 350.dp
) {
    Canvas(
        modifier = modifier
            .size(chartSize)
    ) {

    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ArcComparisonStatPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            DoughnutChart(
                modifier = Modifier
            )
        }
    }
}