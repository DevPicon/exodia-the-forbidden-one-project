package la.devpicon.android.mydrawingsapplication.draw

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class WorkoutTimerStateTest {

    @Test
    fun pauseAndResume_preserveCountdownContinuity() {
        val state = WorkoutTimerState(totalSteps = 3, stepDurationMillis = 10_000L)

        state.start(nowElapsedRealtimeMillis = 1_000L)
        state.tick(nowElapsedRealtimeMillis = 6_000L)
        state.pause(nowElapsedRealtimeMillis = 8_000L)

        assertFalse(state.isRunning)
        assertEquals(3_000L, state.remainingMillis)

        state.tick(nowElapsedRealtimeMillis = 20_000L)
        assertEquals(3_000L, state.remainingMillis)

        state.start(nowElapsedRealtimeMillis = 30_000L)
        state.tick(nowElapsedRealtimeMillis = 31_000L)

        assertTrue(state.isRunning)
        assertEquals(2_000L, state.remainingMillis)
    }

    @Test
    fun reset_restoresEveryFieldFromRunningAndPausedStates() {
        val state = WorkoutTimerState(totalSteps = 3, stepDurationMillis = 10_000L)

        state.start(nowElapsedRealtimeMillis = 0L)
        state.tick(nowElapsedRealtimeMillis = 12_000L)
        state.reset()

        assertResetState(state)

        state.start(nowElapsedRealtimeMillis = 20_000L)
        state.pause(nowElapsedRealtimeMillis = 24_000L)
        state.reset()

        assertResetState(state)
    }

    @Test
    fun restoredRunningSnapshot_continuesFromMonotonicTimestamp() {
        val original = WorkoutTimerState(totalSteps = 3, stepDurationMillis = 10_000L)
        original.start(nowElapsedRealtimeMillis = 1_000L)
        original.tick(nowElapsedRealtimeMillis = 5_000L)

        val restored = WorkoutTimerState(
            totalSteps = 3,
            stepDurationMillis = 10_000L,
            initialSnapshot = original.snapshot()
        )
        restored.tick(nowElapsedRealtimeMillis = 8_000L)

        assertTrue(restored.isRunning)
        assertEquals(3_000L, restored.remainingMillis)
        assertEquals(0, restored.completedSteps)
    }

    @Test
    fun delayedTick_completesAllElapsedStepsAndStopsAtZero() {
        val state = WorkoutTimerState(totalSteps = 2, stepDurationMillis = 1_000L)

        state.start(nowElapsedRealtimeMillis = 0L)
        state.tick(nowElapsedRealtimeMillis = 2_500L)

        assertTrue(state.isComplete)
        assertFalse(state.isRunning)
        assertEquals(2, state.completedSteps)
        assertEquals(0L, state.remainingMillis)
        assertEquals(1f, state.stepProgress, 0f)
    }

    @Test
    fun oneStepAndMinimumDuration_completeWithoutInvalidProgress() {
        val state = WorkoutTimerState(totalSteps = 1, stepDurationMillis = 1L)

        state.start(nowElapsedRealtimeMillis = 0L)
        state.tick(nowElapsedRealtimeMillis = 1L)

        assertTrue(state.isComplete)
        assertEquals(1f, state.stepProgress, 0f)
        assertEquals(0, state.remainingSeconds)
    }

    @Test
    fun invalidBoundaries_areRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            WorkoutTimerState(totalSteps = 0, stepDurationMillis = 1_000L)
        }
        assertThrows(IllegalArgumentException::class.java) {
            WorkoutTimerState(totalSteps = 1, stepDurationMillis = 0L)
        }
    }

    private fun assertResetState(state: WorkoutTimerState) {
        assertFalse(state.isRunning)
        assertFalse(state.isComplete)
        assertEquals(0, state.completedSteps)
        assertEquals(10_000L, state.remainingMillis)
        assertEquals(0f, state.stepProgress, 0f)
    }
}
