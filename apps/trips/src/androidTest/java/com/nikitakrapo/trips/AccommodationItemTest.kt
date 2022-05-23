package com.nikitakrapo.trips

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.nikitakrapo.trips.components.accommodations.AccommodationItemState
import com.nikitakrapo.trips.components.accommodations.AccommodationsListItem
import org.junit.Rule
import org.junit.Test

class AccommodationItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun expandTest() {
        composeTestRule.setContent {
            AccommodationsListItem(accommodationItemState = AccommodationItemState())
        }

        composeTestRule.onNodeWithTag("mainImage").assertIsDisplayed()

        composeTestRule.onNodeWithTag("expandedImage").assertDoesNotExist()

        composeTestRule.onNodeWithTag("expandButton").performClick()

        composeTestRule.onNodeWithTag("mainImage").assertDoesNotExist()

        composeTestRule.onNodeWithTag("expandedImage").assertIsDisplayed()
    }
}