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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val percentageValueA = percentageA.times(100).toInt()
    val percentageValueB = percentageB.times(100).toInt()

    val percentageTextA = "$percentageValueA%"
    val percentageTextB = "$percentageValueB%"

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .size(chartSize)
    ) {
        drawArcs(strokeWidth, chartColorA, initialAngle, sweepAngleA, chartColorB, sweepAngleB)
        drawTexts(chartColorA, textMeasurer, percentageTextB, percentageTextA)
    }
}

private fun DrawScope.drawTexts(
    chartColorA: Color,
    textMeasurer: TextMeasurer,
    percentageTextB: String,
    percentageTextA: String
) {
    // text style
    val textStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = chartColorA.copy(alpha = 1f)
    )
    // top-left (offset)
    val topLeftA = Offset(
        x = 0f,
        y = 0f
    )

    val textLayoutResult = textMeasurer.measure(
        text = percentageTextB,
        style = textStyle
    )

    val topLeftB = Offset(
        x = size.width - textLayoutResult.size.width,
        y = 0f
    )
    // draw text
    drawText(
        text = percentageTextA,
        textMeasurer = textMeasurer,
        topLeft = topLeftA,
        style = textStyle
    )
    drawText(
        text = percentageTextB,
        textMeasurer = textMeasurer,
        topLeft = topLeftB,
        style = textStyle
    )
}

private fun DrawScope.drawArcs(
    strokeWidth: Dp,
    chartColorA: Color,
    initialAngle: Float,
    sweepAngleA: Float,
    chartColorB: Color,
    sweepAngleB: Float
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