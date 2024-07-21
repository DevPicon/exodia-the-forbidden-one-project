package la.devpicon.android.mydrawingsapplication.composable

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

@Composable
fun StatComparison(
    blueTeamValue: Int,
    redTeamValue: Int,
    modifier: Modifier = Modifier
) {

    val dividerColor = if(isSystemInDarkTheme()){
        Color.White
    } else {
        Color.Gray
    }

    val animationPercentage = remember {
        AnimationState(0F)
    }

    LaunchedEffect(key1 = Unit) {
        animationPercentage.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        )
    }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(48.dp)
    ) {
        val midHeight = size.height.div(2)
        val totalValue = blueTeamValue + redTeamValue
        val blueTeamPercentage = blueTeamValue.toFloat() / totalValue
        val dividingPoint = size.width.times(blueTeamPercentage)
        val lineWidth = 4.dp.toPx()

        // Draw blue line from start to dividing point
        drawBlueLine(midHeight, dividingPoint, lineWidth, animationPercentage.value)
        // Draw red line from start to ending point
        drawRedLine(dividingPoint, midHeight, lineWidth, animationPercentage.value)

        drawDivider(lineWidth, dividingPoint, midHeight, dividerColor)


    }
}

private fun DrawScope.drawDivider(
    lineWidth: Float,
    dividingPoint: Float,
    midHeight: Float,
    dividerColor: Color
) {
    val dividerOffsetPx = lineWidth.times(2)
    
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

val totalLength = (size.width - dividingPoint)
    val lengthToRender = totalLength * animationPercentage
    val endingX = dividingPoint + lengthToRender

    drawLine(
        color = Color.Red,
        start = Offset(
            x = dividingPoint,
            y = midHeight
        ),
        end = Offset(
            x = endingX,
            y = midHeight
        ),
        strokeWidth = lineWidth
    )
}

private fun DrawScope.drawBlueLine(
    midHeight: Float,
    dividingPoint: Float,
    lineWidth: Float,
    animationPercentage: Float
) {

    val totalLength = dividingPoint
    val lineLengthToRender = animationPercentage * totalLength
    val startingX = (dividingPoint - lineLengthToRender)

    drawLine(
        color = Color.Blue,
        start = Offset(
            x = startingX,
            y = midHeight
        ),
        end = Offset(
            x = dividingPoint,
            y = midHeight
        ),
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
            )
        }
    }
}