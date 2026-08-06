package la.devpicon.android.mydrawingsapplication.draw

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

class StatComparisonTest {

    @Test
    fun calculateBlueTeamFraction_returnsExpectedProportion() {
        assertEquals(
            0.25f,
            requireNotNull(calculateBlueTeamFraction(25, 75)),
            0.0001f
        )
    }

    @Test
    fun calculateBlueTeamFraction_returnsNeutralStateForZeroTotal() {
        assertNull(calculateBlueTeamFraction(0, 0))
    }

    @Test
    fun calculateBlueTeamFraction_handlesLargeValuesWithoutOverflow() {
        assertEquals(
            0.5f,
            requireNotNull(calculateBlueTeamFraction(Int.MAX_VALUE, Int.MAX_VALUE)),
            0.0001f
        )
    }

    @Test
    fun calculateBlueTeamFraction_rejectsNegativeValues() {
        assertThrows(IllegalArgumentException::class.java) {
            calculateBlueTeamFraction(-1, 10)
        }
    }
}
