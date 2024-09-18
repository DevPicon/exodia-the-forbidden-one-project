package la.devpicon.android.mydrawingsapplication.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun WorkoutPauseTimer(
    modifier: Modifier = Modifier,
    stepCount: Int = 3,
    timeInSeconds: Int = 45,
) {

    var workoutPauseState by remember {
        mutableStateOf(
            WorkoutPauseState(
                timeLeft = timeInSeconds
            )
        )
    }

    LaunchedEffect(key1 = workoutPauseState.isPlaying) {
        if (workoutPauseState.isPlaying) {
            while (workoutPauseState.timeLeft > 0) {
                delay(1000L)

                workoutPauseState = workoutPauseState.copy(
                    timeLeft = workoutPauseState.timeLeft - 1
                )
            }

            workoutPauseState = workoutPauseState.copy(
                isPlaying = false,
                timeLeft = timeInSeconds
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {

        CountdownTimer(
            modifier = modifier,
            timeLeftInSeconds = workoutPauseState.timeLeft
        )

        IconButton(onClick = {
            workoutPauseState = WorkoutPauseState.DEFAULT.copy(
                timeLeft = timeInSeconds
            )
        }) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Reset everything button")
        }

        PlayStopButton(
            modifier = modifier,
            isPlaying = workoutPauseState.isPlaying,
            onPlayStop = {
                workoutPauseState = if (workoutPauseState.isPlaying) {
                    workoutPauseState.copy(
                        isPlaying = false,
                        timeLeft = timeInSeconds
                    )

                } else {
                    workoutPauseState.copy(
                        isPlaying = true
                    )
                }
            }
        )

    }

}

@Composable
fun PlayStopButton(
    modifier: Modifier,
    isPlaying: Boolean,
    onPlayStop: () -> Unit
) {
    val playIcon = painterResource(R.drawable.baseline_play_circle_24)
    val stopIcon = painterResource(R.drawable.baseline_stop_circle_24)

    IconButton(onClick = onPlayStop) {
        Icon(
            painter = if (isPlaying) stopIcon else playIcon,
            contentDescription = if (isPlaying) "Stop timer" else "Start timer",
            modifier = modifier.size(48.dp)
        )
    }

}

@Composable
fun CountdownTimer(
    modifier: Modifier,
    timeLeftInSeconds: Int
) {
    Text(
        text = String.format("%02d:%02d", timeLeftInSeconds / 60, timeLeftInSeconds % 60),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = modifier.testTag("textTimer")
    )
}

data class WorkoutPauseState(
    val isPlaying: Boolean = false,
    val timeLeft: Int = 45
) {
    companion object {
        val DEFAULT = WorkoutPauseState()
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
private fun WorkoutPauseTimerPreview() {
    MyDrawingsApplicationTheme {
        Surface {
            WorkoutPauseTimer()
        }
    }
}