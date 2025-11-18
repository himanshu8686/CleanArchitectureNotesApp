package com.cleanarchitecturenotesapp.feature_note.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import com.cleanarchitecturenotesapp.core.util.TestTags
import com.cleanarchitecturenotesapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun saveNewNote_editAfterwards() {
        val originalTitle = "Test note"
        val originalContent = "Test content"
        val updatedTitle = "Updated note title"
        val updatedContent = "Updated note content"

        // Save a new note.
        composeRule.onNodeWithContentDescription("Add").performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput(originalTitle)
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput(originalContent)
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText(originalTitle).assertIsDisplayed()
        composeRule.onNodeWithText(originalContent).assertIsDisplayed()

        // Open the note and update it.
        composeRule.onNodeWithText(originalTitle).performClick()

        composeRule.waitForIdle()
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals(originalTitle)
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals(originalContent)

        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextReplacement(updatedTitle)
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextReplacement(updatedContent)
        composeRule.onNodeWithContentDescription("Save note").performClick()

        composeRule.waitForIdle()

        composeRule.onNodeWithText(updatedTitle).assertIsDisplayed()
        composeRule.onNodeWithText(updatedContent).assertIsDisplayed()
        composeRule.onNodeWithText(originalTitle).assertDoesNotExist()
    }
}
