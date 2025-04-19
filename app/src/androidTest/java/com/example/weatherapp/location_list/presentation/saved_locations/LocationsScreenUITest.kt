package com.example.weatherapp.location_list.presentation.saved_locations

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.formattedAddress
import com.example.weatherapp.location_list.presentation.LocationsScreen
import com.example.weatherapp.location_list.presentation.LocationListScreenEvent
import com.example.weatherapp.location_list.presentation.LocationListState
import com.example.weatherapp.location_list.presentation.fake.fakeLocations
import com.example.weatherapp.location_list.presentation.fake.fakeUserLocation
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LocationsScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun locationsScreen_SavedLocationsDisplayedCorrectly() {
        composeTestRule.setContent {
            LocationsScreen(
                locationListState = LocationListState(
                    locations = fakeLocations,
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = {}
            )
        }

        fakeLocations.forEachIndexed { index, locationWeatherBrief ->
            composeTestRule
                .onNodeWithTag(SAVED_LOCATIONS_LIST)
                .performScrollToIndex(index)

            composeTestRule
                .onNodeWithText(locationWeatherBrief.location.address.formattedAddress())
                .assertIsDisplayed()
        }
    }

    @Test
    fun weatherScreen_ClickFloatingActionButton_ExpandsMenu() {
        composeTestRule.setContent {
            LocationsScreen(
                locationListState = LocationListState(
                    locations = fakeLocations,
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
                locationListState = LocationListState(
                    locations = fakeLocations,
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        else -> Unit
                    }
                }
            )
        }

        val formattedLocationAddress = fakeLocations[1].location.address.formattedAddress()

        composeTestRule
            .onNodeWithTag(SAVED_LOCATIONS_LIST)
            .performScrollToIndex(1)

        composeTestRule
            .onNodeWithText(formattedLocationAddress)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(formattedLocationAddress)
            .performTouchInput {
                swipeLeft()
            }

        composeTestRule
            .onNodeWithText(formattedLocationAddress)
            .assertIsNotDisplayed()
    }

    @Test
    fun weatherScreen_GetUserLocation_NewLocationIsDisplayed() {
        val locations = mutableStateOf(fakeLocations)
        composeTestRule.setContent {
            LocationsScreen(
                locationListState = LocationListState(
                    locations = locations.value,
                ),
                selectedMapLocation = null,
                onLocationScreenEvent = { event ->
                    when (event) {
                        is LocationListScreenEvent.FetchUserLocation -> {
                            locations.value += fakeUserLocation
                        }
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
            .performClick()


        composeTestRule
            .onNodeWithTag(SAVED_LOCATIONS_LIST)
            .performScrollToIndex(fakeLocations.size - 1)

        composeTestRule
            .onNodeWithText(fakeUserLocation.location.address.formattedAddress())
            .assertIsDisplayed()

    }

    companion object {
        private const val SAVED_LOCATIONS_LIST = "saved_locations_list"
    }
}