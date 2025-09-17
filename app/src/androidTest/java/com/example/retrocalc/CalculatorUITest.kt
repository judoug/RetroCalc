package com.example.retrocalc

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasicAddition() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 12 + 7 = = → 26
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("2").performClick()
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithContentDescription("7").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify display shows 26
        composeTestRule.onNodeWithText("26").assertExists()
    }

    @Test
    fun testMultiplication() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 42 × 10 = → 420
        composeTestRule.onNodeWithContentDescription("4").performClick()
        composeTestRule.onNodeWithContentDescription("2").performClick()
        composeTestRule.onNodeWithContentDescription("Multiply").performClick()
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify display shows 420
        composeTestRule.onNodeWithText("420").assertExists()
    }

    @Test
    fun testPercentageCalculation() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 5 + 50% = → 7.5
        composeTestRule.onNodeWithContentDescription("5").performClick()
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithContentDescription("5").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Percent").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify display shows 7.5
        composeTestRule.onNodeWithText("7.5").assertExists()
    }

    @Test
    fun testDivisionByZero() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 9 ÷ 0 = → Error
        composeTestRule.onNodeWithContentDescription("9").performClick()
        composeTestRule.onNodeWithContentDescription("Divide").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify display shows Error
        composeTestRule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun testErrorStateResets() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Create error state
        composeTestRule.onNodeWithContentDescription("9").performClick()
        composeTestRule.onNodeWithContentDescription("Divide").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify error state
        composeTestRule.onNodeWithText("Error").assertExists()

        // Press a digit to reset error
        composeTestRule.onNodeWithContentDescription("5").performClick()

        // Verify error is cleared and display shows 5
        composeTestRule.onNodeWithText("5").assertExists()
        composeTestRule.onNodeWithText("Error").assertDoesNotExist()
    }

    @Test
    fun testClearButton() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Enter some numbers
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("2").performClick()
        composeTestRule.onNodeWithContentDescription("3").performClick()

        // Verify display shows 123
        composeTestRule.onNodeWithText("123").assertExists()

        // Press clear
        composeTestRule.onNodeWithContentDescription("Clear").performClick()

        // Verify display shows 0
        composeTestRule.onNodeWithText("0").assertExists()
    }

    @Test
    fun testSignToggle() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Enter a number
        composeTestRule.onNodeWithContentDescription("5").performClick()

        // Toggle sign
        composeTestRule.onNodeWithContentDescription("Plus minus").performClick()

        // Verify display shows -5
        composeTestRule.onNodeWithText("-5").assertExists()

        // Toggle again
        composeTestRule.onNodeWithContentDescription("Plus minus").performClick()

        // Verify display shows 5
        composeTestRule.onNodeWithText("5").assertExists()
    }

    @Test
    fun testDecimalInput() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Enter decimal number
        composeTestRule.onNodeWithContentDescription("3").performClick()
        composeTestRule.onNodeWithContentDescription("Decimal point").performClick()
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("4").performClick()

        // Verify display shows 3.14
        composeTestRule.onNodeWithText("3.14").assertExists()
    }

    @Test
    fun testOperationChaining() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 10 + 5 × 2 = → 30
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Add").performClick()
        composeTestRule.onNodeWithContentDescription("5").performClick()
        composeTestRule.onNodeWithContentDescription("Multiply").performClick()
        composeTestRule.onNodeWithContentDescription("2").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick()

        // Verify display shows 30
        composeTestRule.onNodeWithText("30").assertExists()
    }

    @Test
    fun testRepeatedEqualsWithSubtraction() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test: 10 - 3 = = = → 1
        composeTestRule.onNodeWithContentDescription("1").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("Subtract").performClick()
        composeTestRule.onNodeWithContentDescription("3").performClick()
        composeTestRule.onNodeWithContentDescription("Equals").performClick() // 7
        composeTestRule.onNodeWithContentDescription("Equals").performClick() // 4
        composeTestRule.onNodeWithContentDescription("Equals").performClick() // 1

        // Verify display shows 1
        composeTestRule.onNodeWithText("1").assertExists()
    }

    @Test
    fun testWideZeroKey() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test wide 0 key
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()
        composeTestRule.onNodeWithContentDescription("0").performClick()

        // Verify display shows 0 (should not accumulate zeros)
        composeTestRule.onNodeWithText("0").assertExists()
    }

    @Test
    fun testTapeTextDisplay() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Enter operation to show tape text
        composeTestRule.onNodeWithContentDescription("4").performClick()
        composeTestRule.onNodeWithContentDescription("2").performClick()
        composeTestRule.onNodeWithContentDescription("Multiply").performClick()

        // Verify tape text shows "42×"
        composeTestRule.onNodeWithText("42×").assertExists()
    }

    @Test
    fun testAllKeysAreClickable() {
        composeTestRule.setContent {
            RetroCalcTheme {
                CalculatorScreen()
            }
        }

        // Test all number keys
        val numbers = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        numbers.forEach { number ->
            composeTestRule.onNodeWithContentDescription(number).assertIsEnabled()
        }

        // Test operator keys
        val operators = listOf("Add", "Subtract", "Multiply", "Divide", "Equals")
        operators.forEach { operator ->
            composeTestRule.onNodeWithContentDescription(operator).assertIsEnabled()
        }

        // Test utility keys
        val utilities = listOf("Clear", "Plus minus", "Percent", "Decimal point")
        utilities.forEach { utility ->
            composeTestRule.onNodeWithContentDescription(utility).assertIsEnabled()
        }
    }
}
