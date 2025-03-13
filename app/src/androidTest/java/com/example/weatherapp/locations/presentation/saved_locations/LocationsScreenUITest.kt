package com.example.weatherapp.locations.presentation.saved_locations

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.R
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeSavedLocations
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationsScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun locationsScreen_SavedLocationsDisplayedCorrectly() {
        composeTestRule.setContent {
            LocationsScreen(
                locationsState = LocationsState(
                    savedLocations = fakeSavedLocations
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = {}
            )
        }

        fakeSavedLocations.forEachIndexed { index, location ->
            val fullLocationName = if (location.address.country != null) "${location.address.name}, ${location.address.country}" else location.address.name
            composeTestRule
                .onNodeWithTag(SAVED_LOCATIONS_LIST)
                .performScrollToIndex(index)

            composeTestRule
                .onNodeWithText(fullLocationName)
                .assertIsDisplayed()
                .assert(
                    hasAnyChild(
                        hasContentDescription(context.getString(R.string.delete_location))
                    )
                )
        }
    }

    @Test
    fun locationsScreen_ClickLocation_MakesLocationActive() {
        val selectedLocationId = mutableStateOf<String?>(null)
        composeTestRule.setContent {
            LocationsScreen(
                locationsState = LocationsState(
                    savedLocations = fakeSavedLocations,
                    selectedLocationId = selectedLocationId.value
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        is LocationsScreenEvent.SetLocationAsActive -> {
                            selectedLocationId.value = event.locationId
                        }
                        else -> Unit
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithTag(SAVED_LOCATIONS_LIST)
            .performScrollToIndex(4)

        val location = fakeSavedLocations[4]
        val fullLocationName = if (location.address.country != null) "${location.address.name}, ${location.address.country}" else location.address.name

        composeTestRule
            .onNodeWithText(fullLocationName)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.selected_location))
            .assert(hasText(fullLocationName))
            .assertIsDisplayed()

    }

    @Test
    fun weatherScreen_ClickFloatingActionButton_ExpandsMenu() {
        composeTestRule.setContent {
            LocationsScreen(
                locationsState = LocationsState(
                    savedLocations = fakeSavedLocations,
                    selectedLocationId = null
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        else -> Unit
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.new_location), useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.my_location))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.pick_from_map))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.search_for_location))
            .assertIsDisplayed()
    }

    @Test
    fun weatherScreen_SwipeLocationToTheLeft_DeletesLocation() {
        composeTestRule.setContent {
            LocationsScreen(
                locationsState = LocationsState(
                    savedLocations = fakeSavedLocations,
                    selectedLocationId = null
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        else -> Unit
                    }
                }
            )
        }

        val location = fakeSavedLocations[3]
        val fullLocationName = if (location.address.country != null) "${location.address.name}, ${location.address.country}" else location.address.name

        composeTestRule
            .onNodeWithTag(SAVED_LOCATIONS_LIST)
            .performScrollToIndex(3)

        composeTestRule
            .onNodeWithText(fullLocationName)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(fullLocationName)
            .performTouchInput {
                swipeLeft()
            }

        composeTestRule
            .onNodeWithText(fullLocationName)
            .assertIsNotDisplayed()
    }

    @Test
    fun weatherScreen_SwipeLocationToTheRight_MakesLocationActive() {
        val selectedLocationId = mutableStateOf<String?>(null)
        composeTestRule.setContent {
            LocationsScreen(
                locationsState = LocationsState(
                    savedLocations = fakeSavedLocations,
                    selectedLocationId = selectedLocationId.value
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        is LocationsScreenEvent.SetLocationAsActive -> {
                            selectedLocationId.value = event.locationId
                        }
                        else -> Unit
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithTag(SAVED_LOCATIONS_LIST)
            .performScrollToIndex(4)

        val location = fakeSavedLocations[4]
        val fullLocationName = if (location.address.country != null) "${location.address.name}, ${location.address.country}" else location.address.name

        composeTestRule
            .onNodeWithText(fullLocationName)
            .performTouchInput {
                swipeRight()
            }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.selected_location))
            .assert(hasText(fullLocationName))
            .assertIsDisplayed()
    }

    companion object {
        private const val SAVED_LOCATIONS_LIST = "saved_locations_list"
    }
}