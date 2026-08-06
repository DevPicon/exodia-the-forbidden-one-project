package la.devpicon.android.mydrawingsapplication.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal const val SCRATCH_CARD_OVERLAY_TEST_TAG = "scratchCardOverlay"
internal val ScratchStrokeCountKey = SemanticsPropertyKey<Int>("ScratchStrokeCount")
private var SemanticsPropertyReceiver.scratchStrokeCount by ScratchStrokeCountKey

private data class ScratchStroke(
    val points: List<Offset>
)

@Composable
fun ScratchCard(
    modifier: Modifier = Modifier,
    overlayColor: Color = Color(0xFFC0C0C0),
    strokeWidth: Dp = 60.dp,
    content: @Composable BoxScope.() -> Unit
) {
    val scratchStrokes = remember { mutableStateListOf<ScratchStroke>() }

    Box(modifier = modifier) {
        content()

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            scratchStrokes.add(ScratchStroke(points = listOf(offset)))
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            val strokeIndex = scratchStrokes.lastIndex
                            if (strokeIndex >= 0) {
                                val currentStroke = scratchStrokes[strokeIndex]
                                scratchStrokes[strokeIndex] = currentStroke.copy(
                                    points = currentStroke.points + change.position
                                )
                            }
                        }
                    )
                }
                .semantics {
                    scratchStrokeCount = scratchStrokes.size
                }
                .testTag(SCRATCH_CARD_OVERLAY_TEST_TAG)
        ) {
            drawRect(color = overlayColor)

            scratchStrokes.forEach { stroke ->
                if (stroke.points.size == 1) {
                    drawCircle(
                        color = Color.Transparent,
                        radius = strokeWidth.toPx() / 2f,
                        center = stroke.points.first(),
                        blendMode = BlendMode.Clear
                    )
                } else {
                    val scratchPath = Path().apply {
                        val firstPoint = stroke.points.first()
                        moveTo(firstPoint.x, firstPoint.y)
                        stroke.points.drop(1).forEach { point ->
                            lineTo(point.x, point.y)
                        }
                    }

                    drawPath(
                        path = scratchPath,
                        color = Color.Transparent,
                        style = Stroke(
                            width = strokeWidth.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        ),
                        blendMode = BlendMode.Clear
                    )
                }
            }
        }
    }
}
