package la.devpicon.android.mydrawingsapplication.draw

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.StateRestorationTester
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.test.platform.app.InstrumentationRegistry
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class WorkoutPauseTimerTest {

    @Test
    fun pausedTimer_preservesTimeAcrossSavedStateRestoration() = runComposeUiTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val restorationTester = StateRestorationTester(this)
        var nowMillis = 1_000L
        mainClock.autoAdvance = false

        restorationTester.setContent {
            MyDrawingsApplicationTheme {
                WorkoutPauseTimer(
                    numberOfSteps = 2,
                    timeInSeconds = 10,
                    timeSource = { nowMillis }
                )
            }
        }

        onNodeWithContentDescription(context.getString(R.string.workout_timer_start))
            .performClick()
        nowMillis += 4_000L
        mainClock.advanceTimeBy(200L)
        onNodeWithContentDescription(context.getString(R.string.workout_timer_pause))
            .performClick()

        onNodeWithTag(WORKOUT_TIMER_TEXT_TEST_TAG).assertIsDisplayed()
        onNodeWithText("00:06").assertIsDisplayed()

        restorationTester.emulateSaveAndRestore()
        mainClock.advanceTimeByFrame()

        onNodeWithText("00:06").assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.workout_timer_start))
            .assertIsDisplayed()
    }

    @Test
    fun runningTimer_continuesAcrossSavedStateRestoration() = runComposeUiTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val restorationTester = StateRestorationTester(this)
        var nowMillis = 1_000L
        mainClock.autoAdvance = false

        restorationTester.setContent {
            MyDrawingsApplicationTheme {
                WorkoutPauseTimer(
                    numberOfSteps = 2,
                    timeInSeconds = 10,
                    timeSource = { nowMillis }
                )
            }
        }

        onNodeWithContentDescription(context.getString(R.string.workout_timer_start))
            .performClick()
        nowMillis += 4_000L
        mainClock.advanceTimeBy(200L)
        onNodeWithText("00:06").assertIsDisplayed()

        restorationTester.emulateSaveAndRestore()
        nowMillis += 2_000L
        mainClock.advanceTimeBy(200L)

        onNodeWithText("00:04").assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.workout_timer_pause))
            .assertIsDisplayed()
    }

    @Test
    fun resetDuringRunning_restoresInitialUiState() = runComposeUiTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        var nowMillis = 1_000L
        mainClock.autoAdvance = false

        setContent {
            MyDrawingsApplicationTheme {
                WorkoutPauseTimer(
                    numberOfSteps = 2,
                    timeInSeconds = 10,
                    timeSource = { nowMillis }
                )
            }
        }

        onNodeWithContentDescription(context.getString(R.string.workout_timer_start))
            .performClick()
        nowMillis += 3_000L
        mainClock.advanceTimeBy(200L)
        onNodeWithContentDescription(context.getString(R.string.workout_timer_reset))
            .performClick()
        mainClock.advanceTimeByFrame()

        onNodeWithText("00:10").assertIsDisplayed()
        onNodeWithText(context.getString(R.string.workout_timer_step_progress, 1, 2))
            .assertIsDisplayed()
        onNodeWithContentDescription(context.getString(R.string.workout_timer_start))
            .assertIsDisplayed()
    }

    @Test
    fun minimumStepCount_rendersWithoutCrashing() = runComposeUiTest {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        setContent {
            MyDrawingsApplicationTheme {
                WorkoutTimerContent(
                    state = WorkoutTimerUiState(
                        isRunning = false,
                        remainingSeconds = 1,
                        totalSteps = 1,
                        completedSteps = 0,
                        stepProgress = 0f,
                        isComplete = false
                    ),
                    onToggleRunning = {},
                    onReset = {}
                )
            }
        }

        onNodeWithTag(WORKOUT_TIMER_STEPS_TEST_TAG).assertIsDisplayed()
        onNodeWithText(context.getString(R.string.workout_timer_step_progress, 1, 1))
            .assertIsDisplayed()
    }
}
