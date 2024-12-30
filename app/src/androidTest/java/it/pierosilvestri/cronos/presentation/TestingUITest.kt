package it.pierosilvestri.cronos.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

class TestingUITest: KoinTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        composeRule.setContent {
            Row(modifier = Modifier.testTag("my_row")) {
                Text(text = "00:")
                Text(text = "00:")
                Text(text = "00")
            }
        }
    }

    @Test
    fun testMyRowDisplaysCorrectText() {

        // Check that the row with the tag "my_row" combines to "00:00:00"
        composeRule.onNodeWithTag("my_row").assertTextEquals("00:00:00")
    }
}