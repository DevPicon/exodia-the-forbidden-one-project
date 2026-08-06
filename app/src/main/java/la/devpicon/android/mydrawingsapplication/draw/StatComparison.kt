package la.devpicon.android.mydrawingsapplication.draw

import android.content.res.Configuration
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

internal fun calculateBlueTeamFraction(
    blueTeamValue: Int,
    redTeamValue: Int
): Float? {
    require(blueTeamValue >= 0 && redTeamValue >= 0) {
        "Team values must be non-negative"
    }

    val totalValue = blueTeamValue.toLong() + redTeamValue.toLong()
    return if (totalValue == 0L) {
        null
    } else {
        blueTeamValue.toFloat() / totalValue.toFloat()
    }
}

@Composable
fun StatComparison(
    blueTeamValue: Int,
    redTeamValue: Int,
    modifier: Modifier = Modifier
) {
    val dividerColor = if (isSystemInDarkTheme()) Color.White else Color.Gray
    val blueTeamFraction = calculateBlueTeamFraction(blueTeamValue, redTeamValue)
    val animationPercentage = remember { AnimationState(0f) }

    LaunchedEffect(Unit) {
        animationPercentage.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        )
    }

    Canvas(modifier = modifier) {
        val midHeight = size.height / 2f
        val lineWidth = 4.dp.toPx()

        if (blueTeamFraction == null) {
            drawLine(
                color = dividerColor.copy(alpha = 0.4f),
                start = Offset(0f, midHeight),
                end = Offset(size.width, midHeight),
                strokeWidth = lineWidth
            )
            drawDivider(
                lineWidth = lineWidth,
                dividingPoint = size.width / 2f,
                midHeight = midHeight,
                dividerColor = dividerColor
            )
        } else {
            val dividingPoint = size.width * blueTeamFraction
            drawBlueLine(
                midHeight = midHeight,
                dividingPoint = dividingPoint,
                lineWidth = lineWidth,
                animationPercentage = animationPercentage.value
            )
            drawRedLine(
                dividingPoint = dividingPoint,
                midHeight = midHeight,
                lineWidth = lineWidth,
                animationPercentage = animationPercentage.value
            )
            drawDivider(
                lineWidth = lineWidth,
                dividingPoint = dividingPoint,
                midHeight = midHeight,
                dividerColor = dividerColor
            )
        }
    }
}

private fun DrawScope.drawDivider(
    lineWidth: Float,
    dividingPoint: Float,
    midHeight: Float,
    dividerColor: Color
) {
    val dividerOffsetPx = lineWidth * 2f

    drawLine(
        color = dividerColor,
        start = Offset(
            x = dividingPoint,
            y = midHeight - dividerOffsetPx
        ),
        end = Offset(
            x = dividingPoint,
            y = midHeight + dividerOffsetPx
        ),
        strokeWidth = lineWidth
    )
}

private fun DrawScope.drawRedLine(
    dividingPoint: Float,
    midHeight: Float,
    lineWidth: Float,
    animationPercentage: Float
) {
    val totalLength = size.width - dividingPoint
    val endingX = dividingPoint + totalLength * animationPercentage

    drawLine(
        color = Color.Red,
        start = Offset(dividingPoint, midHeight),
        end = Offset(endingX, midHeight),
        strokeWidth = lineWidth
    )
}

private fun DrawScope.drawBlueLine(
    midHeight: Float,
    dividingPoint: Float,
    lineWidth: Float,
    animationPercentage: Float
) {
    val startingX = dividingPoint - dividingPoint * animationPercentage

    drawLine(
        color = Color.Blue,
        start = Offset(startingX, midHeight),
        end = Offset(dividingPoint, midHeight),
        strokeWidth = lineWidth
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
private fun StatComparisonPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            StatComparison(
                blueTeamValue = 5,
                redTeamValue = 23,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}
