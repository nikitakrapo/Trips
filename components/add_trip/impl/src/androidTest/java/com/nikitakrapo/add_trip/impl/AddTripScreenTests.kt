package com.nikitakrapo.add_trip.impl

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitakrapo.add_trip.AddTripFeature
import com.nikitakrapo.add_trip.impl.ui.AddTripScreen
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AddTripScreenTests {

    @get:Rule
    val composeTestRule = createComposeRule() //FIXME: add custom activity

    @Test
    fun click_back_arrow_triggers_onBackArrowPressed() {
        val mock = mockk<() -> Unit>()

        composeTestRule.setContent {
            AddTripScreen(
                state = AddTripFeature.State(),
                onBackArrowPressed = mock
            )
        }

        composeTestRule.onNodeWithTag("top_bar_back").performClick()

        verify(exactly = 1) { mock.invoke() }
    }
}