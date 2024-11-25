package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import la.devpicon.android.mydrawingsapplication.draw.WorkoutPauseTimer
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme

@Composable
fun WorkoutTimerScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WorkoutPauseTimer()
    }
}

@Preview
@Composable
private fun WorkoutPauseTimerScreenPreview() {
    MyDrawingsApplicationTheme {
        Surface() {
            WorkoutTimerScreen()
        }
    }
}