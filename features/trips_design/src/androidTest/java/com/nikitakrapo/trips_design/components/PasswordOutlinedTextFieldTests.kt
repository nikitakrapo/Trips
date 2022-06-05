package com.nikitakrapo.trips_design.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.nikitakrapo.trips_design.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PasswordOutlinedTextFieldTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var textFieldCd: String
    private lateinit var toggleButtonCd: String
    private lateinit var label: String
    private var currentText: String? = null

    @Before
    fun setup() {
        composeTestRule.setContent {
            textFieldCd = stringResource(R.string.trips_design_cd_password_input)
            toggleButtonCd = stringResource(R.string.trips_design_cd_toggle_password_visibility)
            label = stringResource(R.string.password_text_field_label)
            var password by remember { mutableStateOf("") }
            PasswordOutlinedTextField(
                value = password, onValueChange = {
                    password = it
                    currentText = it
                }, isError = false
            )
        }
    }

    @Test
    fun initParams_correct() {
        composeTestRule.onNodeWithContentDescription(textFieldCd)
            .assert(hasTextExactly(label, includeEditableText = false))
    }

    @Test
    fun enteredText_passwordHidden() {
        val text = "someText"
        val maskedText = getMaskedText(text)
        composeTestRule.onNodeWithContentDescription(textFieldCd).performTextInput(text)
        // can not check only for editableText with hasTextExactly
        composeTestRule.onNodeWithContentDescription(textFieldCd)
            .assert(hasText(maskedText))
    }

    @Test
    fun enteredText_toggledVisibility_passwordVisible() {
        val text = "someText"
        composeTestRule.onNodeWithContentDescription(textFieldCd).performTextInput(text)
        composeTestRule.onNodeWithContentDescription(toggleButtonCd).performClick()
        // can not check only for editableText with hasTextExactly
        composeTestRule.onNodeWithContentDescription(textFieldCd)
            .assert(hasText(text))
    }

    @Test
    fun enteredText_toggledVisibilityTwice_passwordInvisible() {
        val text = "someText"
        val maskedText = getMaskedText(text)
        composeTestRule.onNodeWithContentDescription(textFieldCd).performTextInput(text)
        composeTestRule.onNodeWithContentDescription(toggleButtonCd).performClick()
        composeTestRule.onNodeWithContentDescription(toggleButtonCd).performClick()
        // can not check only for editableText with hasTextExactly
        composeTestRule.onNodeWithContentDescription(textFieldCd)
            .assert(hasText(maskedText))
    }

    @Suppress("SameParameterValue")
    private fun getMaskedText(text: String): String =
        StringBuilder(PasswordVisualTransformation().mask.toString()).repeat(text.length)
}