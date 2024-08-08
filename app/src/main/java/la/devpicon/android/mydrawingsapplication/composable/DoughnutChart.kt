package la.devpicon.android.mydrawingsapplication.composable

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

private const val TOTAL_ANGLE = 360f

@Composable
fun DoughnutChart(
    modifier: Modifier = Modifier,
    chartSize: Dp = 350.dp,
    percentageA: Float = 0.5f,
    percentageB: Float = 0.5f,
    chartColorA: Color = Color.Red,
    chartColorB: Color = Color.Blue,
    strokeWidth: Dp = 50.dp
) {
    val initialAngle = 270f
    val sweepAngleA: Float = TOTAL_ANGLE.times(percentageA).times(-1) // Counter clockwise
    val sweepAngleB: Float = TOTAL_ANGLE.times(percentageB) // clockwise

    Canvas(
        modifier = modifier
            .size(chartSize)
    ) {

        // arc style
        val arcStyle = Stroke(
            width = strokeWidth.toPx()
        )
        // top left position (offset)
        val topLeft = Offset(
            x = 0f + strokeWidth.toPx() / 2,
            y = 0f + strokeWidth.toPx() / 2
        )
        // arc size
        val arcSize = Size(
            width = size.width - strokeWidth.toPx(),
            height = size.height - strokeWidth.toPx()
        )
        // draw arc
        drawArc(
            color = chartColorA,
            startAngle = initialAngle,
            sweepAngle = sweepAngleA,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = arcStyle
        )
        drawArc(
            color = chartColorB,
            startAngle = initialAngle,
            sweepAngle = sweepAngleB,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = arcStyle
        )

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
            val chartColorA = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            }

            val chartColorB = if (isSystemInDarkTheme()) {
                Color.LightGray
            } else {
                Color.DarkGray
            }

            DoughnutChart(
                modifier = Modifier,
                chartColorA = chartColorA,
                chartColorB = chartColorB,
                percentageA = 0.55f,
                percentageB = 0.45f
            )
        }
    }
}