package la.devpicon.android.mydrawingsapplication.composable.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import la.devpicon.android.mydrawingsapplication.MainActivity
import la.devpicon.android.mydrawingsapplication.R
import org.junit.Rule
import org.junit.Test

class StatComparisonNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeAction_opensStatComparisonAndBackReturnsHome() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val statComparisonLabel = context.getString(R.string.label_stat_comparison)

        composeTestRule.onNodeWithText(statComparisonLabel).performClick()

        composeTestRule.onNodeWithText(statComparisonLabel).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule
            .onNodeWithText(context.getString(R.string.app_name))
            .assertIsDisplayed()
    }
}
