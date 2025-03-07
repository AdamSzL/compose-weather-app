package com.example.weatherapp.weather.presentation.weather

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.R
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherInfo
import com.example.weatherapp.weather.presentation.weather.fake.fakeWeatherTileData
import com.example.weatherapp.weather.presentation.weather.utils.capitalizeWords
import com.example.weatherapp.weather.presentation.weather.utils.convertTimestampToHourMinute
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherScreenUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun weatherScreen_weatherDataIsDisplayedCorrectly() {
        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherHeaderInfo = fakeWeatherHeaderInfo,
                    weatherTileData = fakeWeatherTileData
                ),
                onWeatherScreenEvent = {}
            )
        }

        val weatherHeaderTexts = listOf(
            "${fakeWeatherInfo.cityName}, ${fakeWeatherInfo.country}",
            fakeWeatherInfo.weatherDescription.capitalizeWords(),
            "${fakeWeatherInfo.temperature}°C",
            "${fakeWeatherInfo.minTemperature}°C",
            "${fakeWeatherInfo.maxTemperature}°C",
            context.getString(R.string.feels_like, fakeWeatherInfo.feelsLike),
        )

        weatherHeaderTexts.forEach { text ->
            composeTestRule
                .onNodeWithText(text)
                .assertIsDisplayed()
        }

        val weatherTileLabels = listOf(
            context.getString(R.string.wind_speed),
            context.getString(R.string.wind_direction),
            context.getString(R.string.rain),
            context.getString(R.string.snow),
            context.getString(R.string.pressure),
            context.getString(R.string.humidity),
            context.getString(R.string.cloudiness),
            context.getString(R.string.visibility),
            context.getString(R.string.sunrise),
            context.getString(R.string.sunset),
        )

        weatherTileLabels.forEachIndexed { index, text ->
            composeTestRule
                .onNodeWithTag(TILES_GRID_TAG)
                .performScrollToIndex(index + 1)

            composeTestRule
                .onNodeWithText(text)
                .assertIsDisplayed()
        }

        val weatherTileValues = listOf(
            Pair("%.2f".format(fakeWeatherInfo.windSpeed), 1),
            Pair("%.2f".format(fakeWeatherInfo.rain), 3),
            Pair("%.2f".format(fakeWeatherInfo.snow), 4),
            Pair(fakeWeatherInfo.pressure.toString(), 5),
            Pair(fakeWeatherInfo.humidity.toString(), 6),
            Pair(fakeWeatherInfo.cloudiness.toString(), 7),
            Pair(fakeWeatherInfo.visibility.toString(), 8),
            Pair(convertTimestampToHourMinute(fakeWeatherInfo.sunrise), 9),
            Pair(convertTimestampToHourMinute(fakeWeatherInfo.sunset), 10),
        )

        weatherTileValues.forEach { (value, index) ->
            composeTestRule
                .onNodeWithTag(TILES_GRID_TAG)
                .performScrollToIndex(index)

            composeTestRule
                .onNodeWithText(value)
                .assertIsDisplayed()
        }
    }

    @Test
    fun weatherScreen_EnterAndExitEditMode_CorrectlyUpdatesUiInDifferentModes() {
        val isEditModeEnabled = mutableStateOf(false)

        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherHeaderInfo = fakeWeatherHeaderInfo,
                    weatherTileData = fakeWeatherTileData,
                    isEditModeEnabled = isEditModeEnabled.value,
                ),
                onWeatherScreenEvent = { event ->
                    if (event is WeatherScreenEvent.ToggleEditMode) {
                        isEditModeEnabled.value = event.enabled
                    } else if (event is WeatherScreenEvent.SaveLayoutAndExitEditMode) {
                        isEditModeEnabled.value = false
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.edit), useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.exit_edit_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.edit_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.open_edit_mode_options))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.done), useUnmergedTree = true)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.start_delete_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.done), useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.edit), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun weatherScreen_DeleteTileInDeleteMode_CorrectlyDeletesTile() {
        val weatherTileData = mutableStateOf(fakeWeatherTileData)
        val isEditModeEnabled = mutableStateOf(false)
        val isDeleteModeEnabled = mutableStateOf(false)

        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherHeaderInfo = fakeWeatherHeaderInfo,
                    weatherTileData = weatherTileData.value,
                    isEditModeEnabled = isEditModeEnabled.value,
                    isDeleteModeEnabled = isDeleteModeEnabled.value
                ),
                onWeatherScreenEvent = { event ->
                    when (event) {
                        is WeatherScreenEvent.ToggleEditMode -> {
                            isEditModeEnabled.value = event.enabled
                        }
                        is WeatherScreenEvent.ToggleDeleteMode -> {
                            isDeleteModeEnabled.value = event.enabled
                        }
                        is WeatherScreenEvent.DeleteTile -> {
                            weatherTileData.value =
                                weatherTileData.value.filter { it.tileId != event.tileId }
                        }
                        else -> Unit
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.edit), useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.start_delete_mode))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.exit_delete_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TILES_GRID_TAG)
            .onChildren()
            .filter(!hasTestTag(WEATHER_HEADER_TAG))
            .filter(!hasTestTag(GRID_BOTTOM_SPACER_TAG))
            .assertAll(hasTestTag(SHAKING_TAG))

        composeTestRule
            .onNodeWithTag(TILES_GRID_TAG)
            .performScrollToIndex(1)

        composeTestRule
            .onNodeWithText("%.2f".format(fakeWeatherInfo.windSpeed))
            .performClick()

        composeTestRule
            .onNodeWithText("%.2f".format(fakeWeatherInfo.windSpeed))
            .assertIsNotDisplayed()
    }

    @Test
    fun weatherScreen_EditMode_VerifyDropdownMenuDisplaysCorrectly() {
        val isEditModeEnabled = mutableStateOf(false)
        val isAutoSaveEnabled = mutableStateOf(true)

        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherHeaderInfo = fakeWeatherHeaderInfo,
                    weatherTileData = fakeWeatherTileData,
                    isEditModeEnabled = isEditModeEnabled.value,
                    isAutoSaveEnabled = isAutoSaveEnabled.value
                ),
                onWeatherScreenEvent = { event ->
                    when (event) {
                        is WeatherScreenEvent.ToggleEditMode -> {
                            isEditModeEnabled.value = event.enabled
                        }
                        is WeatherScreenEvent.ToggleAutoSave -> {
                            isAutoSaveEnabled.value = event.checked
                        }
                        else -> Unit
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.edit), useUnmergedTree = true)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.open_edit_mode_options))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.reset_tiles))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.shuffle_tiles))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.start_delete_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.auto_save))
            .performClick()

        composeTestRule
            .onNodeWithText(context.getString(R.string.save_layout))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.undo_layout_change))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.redo_layout_change))
            .assertIsDisplayed()
    }

    companion object {
        private const val TILES_GRID_TAG = "TilesGrid"
        private const val SHAKING_TAG = "Shaking"
        private const val WEATHER_HEADER_TAG = "WeatherHeader"
        private const val GRID_BOTTOM_SPACER_TAG = "GridBottomSpacer"
    }
}
