package la.devpicon.android.mydrawingsapplication.composable

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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

    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(48.dp)
    ) {
        val midHeight = size.height.div(2)
        val totalValue = blueTeamValue + redTeamValue
        val blueTeamPercentage = blueTeamValue.toFloat() / totalValue
        val dividingPoint = size.width.times(blueTeamPercentage)
        val lineWidth = 2.dp.toPx()

        // Draw blue line from start to dividing point
        drawBlueLine(midHeight, dividingPoint, lineWidth)
        // Draw red line from start to ending point
        drawRedLine(dividingPoint, midHeight, lineWidth)

        drawDivider(lineWidth, dividingPoint, midHeight)


    }
}

private fun DrawScope.drawDivider(
    lineWidth: Float,
    dividingPoint: Float,
    midHeight: Float
) {
    val dividerOffsetPx = lineWidth.times(2)
    
    drawLine(
        color = Color.Gray,
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
    lineWidth: Float
) {
    drawLine(
        color = Color.Red,
        start = Offset(
            x = dividingPoint,
            y = midHeight
        ),
        end = Offset(
            x = size.width,
            y = midHeight
        ),
        strokeWidth = lineWidth
    )
}

private fun DrawScope.drawBlueLine(
    midHeight: Float,
    dividingPoint: Float,
    lineWidth: Float
) {
    drawLine(
        color = Color.Blue,
        start = Offset(
            x = 0f,
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
                redTeamValue = 3,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}