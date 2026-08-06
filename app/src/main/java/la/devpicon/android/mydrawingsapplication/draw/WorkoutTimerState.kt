package la.devpicon.android.mydrawingsapplication.draw

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.math.ceil

internal data class WorkoutTimerSnapshot(
    val isRunning: Boolean,
    val remainingMillis: Long,
    val completedSteps: Int,
    val lastUpdateElapsedRealtimeMillis: Long?
)

@Stable
class WorkoutTimerState internal constructor(
    val totalSteps: Int,
    val stepDurationMillis: Long,
    initialSnapshot: WorkoutTimerSnapshot = WorkoutTimerSnapshot(
        isRunning = false,
        remainingMillis = stepDurationMillis,
        completedSteps = 0,
        lastUpdateElapsedRealtimeMillis = null
    )
) {
    init {
        require(totalSteps >= 1) { "Total steps must be at least one" }
        require(stepDurationMillis > 0L) { "Step duration must be positive" }
        require(initialSnapshot.completedSteps in 0..totalSteps) {
            "Completed steps must be within the workout range"
        }
        require(initialSnapshot.remainingMillis in 0L..stepDurationMillis) {
            "Remaining time must be within the step duration"
        }
    }

    var isRunning by mutableStateOf(
        initialSnapshot.isRunning && initialSnapshot.completedSteps < totalSteps
    )
        private set

    var remainingMillis by mutableLongStateOf(
        if (initialSnapshot.completedSteps == totalSteps) {
            0L
        } else {
            initialSnapshot.remainingMillis
        }
    )
        private set

    var completedSteps by mutableIntStateOf(initialSnapshot.completedSteps)
        private set

    private var lastUpdateElapsedRealtimeMillis: Long? =
        initialSnapshot.lastUpdateElapsedRealtimeMillis

    val remainingSeconds: Int
        get() = ceil(remainingMillis / MILLIS_PER_SECOND.toDouble()).toInt()

    val stepProgress: Float
        get() = if (isComplete) {
            1f
        } else {
            (1f - remainingMillis.toFloat() / stepDurationMillis.toFloat())
                .coerceIn(0f, 1f)
        }

    val isComplete: Boolean
        get() = completedSteps == totalSteps

    fun toggle(nowElapsedRealtimeMillis: Long) {
        if (isRunning) {
            pause(nowElapsedRealtimeMillis)
        } else {
            start(nowElapsedRealtimeMillis)
        }
    }

    fun start(nowElapsedRealtimeMillis: Long) {
        if (isRunning) return

        if (isComplete) {
            reset()
        }

        lastUpdateElapsedRealtimeMillis = nowElapsedRealtimeMillis
        isRunning = true
    }

    fun pause(nowElapsedRealtimeMillis: Long) {
        if (!isRunning) return

        tick(nowElapsedRealtimeMillis)
        isRunning = false
        lastUpdateElapsedRealtimeMillis = null
    }

    fun reset() {
        isRunning = false
        remainingMillis = stepDurationMillis
        completedSteps = 0
        lastUpdateElapsedRealtimeMillis = null
    }

    fun tick(nowElapsedRealtimeMillis: Long) {
        if (!isRunning) return

        val previousUpdate = lastUpdateElapsedRealtimeMillis ?: nowElapsedRealtimeMillis
        val elapsedMillis = if (nowElapsedRealtimeMillis >= previousUpdate) {
            nowElapsedRealtimeMillis - previousUpdate
        } else {
            0L
        }
        lastUpdateElapsedRealtimeMillis = nowElapsedRealtimeMillis
        consumeElapsedTime(elapsedMillis)
    }

    internal fun snapshot(): WorkoutTimerSnapshot = WorkoutTimerSnapshot(
        isRunning = isRunning,
        remainingMillis = remainingMillis,
        completedSteps = completedSteps,
        lastUpdateElapsedRealtimeMillis = lastUpdateElapsedRealtimeMillis
    )

    private fun consumeElapsedTime(elapsedMillis: Long) {
        var timeToConsume = elapsedMillis

        while (isRunning && timeToConsume >= remainingMillis) {
            timeToConsume -= remainingMillis
            completedSteps += 1

            if (isComplete) {
                remainingMillis = 0L
                isRunning = false
                lastUpdateElapsedRealtimeMillis = null
            } else {
                remainingMillis = stepDurationMillis
            }
        }

        if (isRunning) {
            remainingMillis -= timeToConsume
        }
    }
}

@Composable
fun rememberWorkoutTimerState(
    totalSteps: Int,
    stepDurationMillis: Long
): WorkoutTimerState {
    require(totalSteps >= 1) { "Total steps must be at least one" }
    require(stepDurationMillis > 0L) { "Step duration must be positive" }

    return rememberSaveable(
        totalSteps,
        stepDurationMillis,
        saver = workoutTimerStateSaver(totalSteps, stepDurationMillis)
    ) {
        WorkoutTimerState(
            totalSteps = totalSteps,
            stepDurationMillis = stepDurationMillis
        )
    }
}

private fun workoutTimerStateSaver(
    totalSteps: Int,
    stepDurationMillis: Long
) = listSaver<WorkoutTimerState, Any>(
    save = { state ->
        val snapshot = state.snapshot()
        listOf(
            snapshot.isRunning,
            snapshot.remainingMillis,
            snapshot.completedSteps,
            snapshot.lastUpdateElapsedRealtimeMillis ?: NO_TIMESTAMP
        )
    },
    restore = { values ->
        WorkoutTimerState(
            totalSteps = totalSteps,
            stepDurationMillis = stepDurationMillis,
            initialSnapshot = WorkoutTimerSnapshot(
                isRunning = values[0] as Boolean,
                remainingMillis = values[1] as Long,
                completedSteps = values[2] as Int,
                lastUpdateElapsedRealtimeMillis = (values[3] as Long)
                    .takeUnless { it == NO_TIMESTAMP }
            )
        )
    }
)

private const val MILLIS_PER_SECOND = 1_000L
private const val NO_TIMESTAMP = -1L
