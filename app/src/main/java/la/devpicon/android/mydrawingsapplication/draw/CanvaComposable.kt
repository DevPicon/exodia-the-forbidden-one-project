package la.devpicon.android.mydrawingsapplication.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun CanvasComposable(
    modifier: Modifier = Modifier
) {

    val text = "Hola Android"
    val textMeasurer = rememberTextMeasurer()

    val style = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Blue
    )

    val textLayoutResult = remember(text) {
        textMeasurer.measure(text, style = style)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {

        val width = size.width
        val height = size.height

        val rectWidth = width / 2

        drawRect(
            color = Color.Green,
            topLeft = Offset(
                x = (width - rectWidth) / 2,
                y = (height - rectWidth) / 2
            ),
            size = Size(
                width = rectWidth,
                height = rectWidth
            )
        )

        drawText(
            text = text,
            textMeasurer = textMeasurer,
            topLeft = Offset(
                x = width / 2 - textLayoutResult.size.width / 2,
                y = height / 2 - textLayoutResult.size.height
            ),
            style = style
        )

    }
}

@Composable
fun DrawBehindText(modifier: Modifier = Modifier) {
    val text = "Hola Android"
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val width = size.width
                val height = size.height

                val rectWidth = width / 2

                drawRect(
                    color = Color.Green,
                    topLeft = Offset(
                        x = (width - rectWidth) / 2,
                        y = (height - rectWidth) / 2
                    ),
                    size = Size(
                        width = rectWidth,
                        height = rectWidth
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CanvasComposablePreview() {
    MyDrawingsApplicationTheme {
        CanvasComposable()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DrawBehindTextPreview(modifier: Modifier = Modifier) {
    MyDrawingsApplicationTheme {
        DrawBehindText()
    }
}