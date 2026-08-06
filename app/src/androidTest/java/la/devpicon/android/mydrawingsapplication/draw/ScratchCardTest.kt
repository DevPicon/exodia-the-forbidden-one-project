package la.devpicon.android.mydrawingsapplication.draw

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.unit.dp
import la.devpicon.android.mydrawingsapplication.composable.screen.SCRATCH_CARD_RESET_TEST_TAG
import la.devpicon.android.mydrawingsapplication.composable.screen.ScratchCardScreen
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import org.junit.Rule
import org.junit.Test

class ScratchCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun contentSlot_isEmitted() {
        composeTestRule.setContent {
            ScratchCard(modifier = Modifier.size(240.dp, 120.dp)) {
                Text(text = "Hidden prize")
            }
        }

        composeTestRule.onNodeWithText("Hidden prize").assertIsDisplayed()
    }

    @Test
    fun draggingOverlay_recordsScratchStroke() {
        composeTestRule.setContent {
            ScratchCard(modifier = Modifier.size(240.dp, 120.dp)) {
                Text(text = "Hidden prize")
            }
        }

        val overlay = composeTestRule.onNodeWithTag(SCRATCH_CARD_OVERLAY_TEST_TAG)
        overlay.assert(SemanticsMatcher.expectValue(ScratchStrokeCountKey, 0))

        overlay.performTouchInput { swipeLeft() }

        overlay.assert(SemanticsMatcher.expectValue(ScratchStrokeCountKey, 1))
    }

    @Test
    fun resetAction_recreatesCoveredOverlay() {
        composeTestRule.setContent {
            MyDrawingsApplicationTheme {
                ScratchCardScreen()
            }
        }

        composeTestRule
            .onNodeWithTag(SCRATCH_CARD_OVERLAY_TEST_TAG)
            .performTouchInput { swipeLeft() }
            .assert(SemanticsMatcher.expectValue(ScratchStrokeCountKey, 1))

        composeTestRule.onNodeWithTag(SCRATCH_CARD_RESET_TEST_TAG).performClick()

        composeTestRule
            .onNodeWithTag(SCRATCH_CARD_OVERLAY_TEST_TAG)
            .assert(SemanticsMatcher.expectValue(ScratchStrokeCountKey, 0))
    }
}
