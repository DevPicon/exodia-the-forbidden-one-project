package la.devpicon.android.mydrawingsapplication.draw

import android.content.res.Configuration
import android.os.SystemClock
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import java.util.Locale

internal const val WORKOUT_TIMER_TEXT_TEST_TAG = "workoutTimerText"
internal const val WORKOUT_TIMER_TOGGLE_TEST_TAG = "workoutTimerToggle"
internal const val WORKOUT_TIMER_RESET_TEST_TAG = "workoutTimerReset"
internal const val WORKOUT_TIMER_STEPS_TEST_TAG = "workoutTimerSteps"

internal data class WorkoutTimerUiState(
    val isRunning: Boolean,
    val remainingSeconds: Int,
    val totalSteps: Int,
    val completedSteps: Int,
    val stepProgress: Float,
    val isComplete: Boolean
)

@Composable
fun WorkoutPauseTimer(
    modifier: Modifier = Modifier,
    numberOfSteps: Int = 5,
    timeInSeconds: Int = 30,
    timeSource: () -> Long = SystemClock::elapsedRealtime
) {
    require(numberOfSteps >= 1) { "Number of steps must be at least one" }
    require(timeInSeconds > 0) { "Time in seconds must be positive" }

    val timerState = rememberWorkoutTimerState(
        totalSteps = numberOfSteps,
        stepDurationMillis = timeInSeconds.toLong() * 1_000L
    )
    val latestTimeSource by rememberUpdatedState(timeSource)

    LaunchedEffect(timerState, timerState.isRunning) {
        while (timerState.isRunning) {
            delay(TIMER_REFRESH_INTERVAL_MILLIS)
            timerState.tick(latestTimeSource())
        }
    }

    WorkoutTimerContent(
        state = WorkoutTimerUiState(
            isRunning = timerState.isRunning,
            remainingSeconds = timerState.remainingSeconds,
            totalSteps = timerState.totalSteps,
            completedSteps = timerState.completedSteps,
            stepProgress = timerState.stepProgress,
            isComplete = timerState.isComplete
        ),
        onToggleRunning = { timerState.toggle(latestTimeSource()) },
        onReset = timerState::reset,
        modifier = modifier
    )
}

@Composable
internal fun WorkoutTimerContent(
    state: WorkoutTimerUiState,
    onToggleRunning: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CountdownTimer(timeLeftInSeconds = state.remainingSeconds)

            IconButton(
                onClick = onReset,
                modifier = Modifier.testTag(WORKOUT_TIMER_RESET_TEST_TAG)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.workout_timer_reset)
                )
            }

            PlayPauseButton(
                isRunning = state.isRunning,
                onToggleRunning = onToggleRunning,
                modifier = Modifier.testTag(WORKOUT_TIMER_TOGGLE_TEST_TAG)
            )
        }

        WorkoutSteps(
            numberOfSteps = state.totalSteps,
            completedSteps = state.completedSteps,
            isRunning = state.isRunning,
            stepProgress = state.stepProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .testTag(WORKOUT_TIMER_STEPS_TEST_TAG)
        )

        Text(
            text = if (state.isComplete) {
                stringResource(R.string.workout_timer_complete)
            } else {
                stringResource(
                    R.string.workout_timer_step_progress,
                    (state.completedSteps + 1).coerceAtMost(state.totalSteps),
                    state.totalSteps
                )
            },
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
internal fun WorkoutSteps(
    numberOfSteps: Int,
    completedSteps: Int,
    isRunning: Boolean,
    stepProgress: Float,
    modifier: Modifier = Modifier
) {
    require(numberOfSteps >= 1) { "Number of steps must be at least one" }
    require(completedSteps in 0..numberOfSteps) {
        "Completed steps must be within the workout range"
    }

    val textMeasurer = rememberTextMeasurer()
    val inactiveColor = Color(0xFFE5E5E5)
    val activeColor = Color(0xFF55CEFF)

    Canvas(modifier = modifier) {
        val desiredRadius = 16.dp.toPx()
        val radius = minOf(desiredRadius, size.width / (numberOfSteps * 2f))
        val centerY = size.height - radius
        val availableWidth = (size.width - radius * 2f).coerceAtLeast(0f)
        val stepWidth = if (numberOfSteps == 1) 0f else availableWidth / (numberOfSteps - 1)
        val centers = List(numberOfSteps) { index ->
            Offset(
                x = if (numberOfSteps == 1) size.width / 2f else radius + index * stepWidth,
                y = centerY
            )
        }

        drawStepConnections(
            centers = centers,
            radius = radius,
            completedSteps = completedSteps,
            inactiveColor = inactiveColor,
            activeColor = activeColor
        )

        centers.forEachIndexed { index, center ->
            val isCompleted = index < completedSteps
            drawCircle(
                color = if (isCompleted) activeColor else inactiveColor,
                radius = radius,
                center = center
            )

            if (isCompleted) {
                drawCheckmark(center = center, radius = radius)
            } else {
                drawNumberInCircle(index = index, textMeasurer = textMeasurer, center = center)
            }
        }

        if (isRunning && completedSteps < numberOfSteps) {
            val center = centers[completedSteps]
            val strokeWidth = 3.dp.toPx()
            drawArc(
                color = activeColor,
                startAngle = -90f,
                sweepAngle = 360f * stepProgress.coerceIn(0f, 1f),
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2f, radius * 2f),
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

private fun DrawScope.drawStepConnections(
    centers: List<Offset>,
    radius: Float,
    completedSteps: Int,
    inactiveColor: Color,
    activeColor: Color
) {
    centers.zipWithNext().forEachIndexed { index, (start, end) ->
        val startOffset = Offset(start.x + radius + 2f, start.y)
        val endOffset = Offset(end.x - radius - 2f, end.y)
        drawLine(
            color = if (index < completedSteps - 1) activeColor else inactiveColor,
            start = startOffset,
            end = endOffset,
            strokeWidth = if (index < completedSteps - 1) 8f else 14f
        )
    }
}

private fun DrawScope.drawCheckmark(center: Offset, radius: Float) {
    drawPath(
        path = Path().apply {
            moveTo(center.x - radius * 0.5f, center.y)
            lineTo(center.x - radius * 0.1f, center.y + radius * 0.4f)
            lineTo(center.x + radius * 0.6f, center.y - radius * 0.45f)
        },
        brush = SolidColor(Color.Black),
        style = Stroke(width = 2.dp.toPx())
    )
}

private fun DrawScope.drawNumberInCircle(
    index: Int,
    textMeasurer: TextMeasurer,
    center: Offset
) {
    val text = (index + 1).toString()
    val style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold)
    val layoutResult = textMeasurer.measure(text = text, style = style)

    drawText(
        text = text,
        textMeasurer = textMeasurer,
        topLeft = Offset(
            x = center.x - layoutResult.size.width / 2f,
            y = center.y - layoutResult.size.height / 2f
        ),
        style = style
    )
}

@Composable
private fun PlayPauseButton(
    isRunning: Boolean,
    onToggleRunning: () -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = painterResource(
        if (isRunning) R.drawable.baseline_stop_circle_24
        else R.drawable.baseline_play_circle_24
    )
    val description = stringResource(
        if (isRunning) R.string.workout_timer_pause
        else R.string.workout_timer_start
    )

    IconButton(onClick = onToggleRunning, modifier = modifier) {
        Icon(
            painter = icon,
            contentDescription = description,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun CountdownTimer(
    timeLeftInSeconds: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = String.format(
            Locale.ROOT,
            "%02d:%02d",
            timeLeftInSeconds / 60,
            timeLeftInSeconds % 60
        ),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        modifier = modifier.testTag(WORKOUT_TIMER_TEXT_TEST_TAG)
    )
}

@Preview(name = "Night Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun WorkoutPauseTimerPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            WorkoutTimerContent(
                state = WorkoutTimerUiState(
                    isRunning = true,
                    remainingSeconds = 18,
                    totalSteps = 5,
                    completedSteps = 2,
                    stepProgress = 0.4f,
                    isComplete = false
                ),
                onToggleRunning = {},
                onReset = {}
            )
        }
    }
}

private const val TIMER_REFRESH_INTERVAL_MILLIS = 100L
