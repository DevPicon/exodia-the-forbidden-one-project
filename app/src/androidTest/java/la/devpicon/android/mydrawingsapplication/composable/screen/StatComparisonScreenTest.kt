package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performSemanticsAction
import androidx.test.platform.app.InstrumentationRegistry
import la.devpicon.android.mydrawingsapplication.R
import la.devpicon.android.mydrawingsapplication.ui.theme.MyDrawingsApplicationTheme
import org.junit.Rule
import org.junit.Test

class StatComparisonScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sliders_updateDisplayedValues() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.setContent {
            MyDrawingsApplicationTheme {
                StatComparisonScreen()
            }
        }

        composeTestRule
            .onNodeWithTag(BLUE_TEAM_SLIDER_TEST_TAG)
            .performSemanticsAction(SemanticsActions.SetProgress) { setProgress ->
                setProgress(75f)
            }

        composeTestRule
            .onNodeWithText(context.getString(R.string.stat_comparison_blue_value, 75))
            .assertIsDisplayed()
    }

    @Test
    fun zeroValues_showDocumentedNeutralState() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        composeTestRule.setContent {
            MyDrawingsApplicationTheme {
                StatComparisonScreen()
            }
        }

        composeTestRule
            .onNodeWithTag(BLUE_TEAM_SLIDER_TEST_TAG)
            .performSemanticsAction(SemanticsActions.SetProgress) { it(0f) }
        composeTestRule
            .onNodeWithTag(RED_TEAM_SLIDER_TEST_TAG)
            .performSemanticsAction(SemanticsActions.SetProgress) { it(0f) }

        composeTestRule
            .onNodeWithText(context.getString(R.string.stat_comparison_empty_values))
            .assertIsDisplayed()
    }
}
