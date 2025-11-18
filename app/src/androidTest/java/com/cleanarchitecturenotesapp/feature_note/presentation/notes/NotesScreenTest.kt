package com.cleanarchitecturenotesapp.feature_note.presentation.notes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.cleanarchitecturenotesapp.core.util.TestTags
import com.cleanarchitecturenotesapp.di.AppModule
import com.cleanarchitecturenotesapp.feature_note.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun clickToggleOrderSection_isVisible(){
        composeRule.onNodeWithTag(testTag = TestTags.ORDER_SECTION)
            .assertDoesNotExist()

        composeRule.onNodeWithContentDescription(label = "Sort")
            .performClick()

        composeRule.onNodeWithTag(testTag = TestTags.ORDER_SECTION)
            .assertIsDisplayed()
    }
}