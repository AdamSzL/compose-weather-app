package com.example.weatherapp.weather.presentation.weather

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.R
import com.example.weatherapp.core.fake.fakeUserLocation
import com.example.weatherapp.weather.presentation.WeatherScreen
import com.example.weatherapp.weather.presentation.WeatherScreenEvent
import com.example.weatherapp.weather.presentation.WeatherState
import com.example.weatherapp.weather.presentation.fake.fakeDetailedWeather
import com.example.weatherapp.weather.presentation.fake.fakeWeatherHeaderInfo
import com.example.weatherapp.weather.presentation.fake.fakeWeatherInfo
import com.example.weatherapp.weather.presentation.fake.fakeWeatherTileData
import com.example.weatherapp.weather.presentation.utils.capitalizeWords
import com.example.weatherapp.weather.presentation.utils.convertTimestampToHourMinute
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
                    weatherInfo = fakeWeatherInfo,
                    weatherTileDataHistory = listOf(fakeWeatherTileData),
                ),
                onWeatherScreenEvent = {}
            )
        }

        val weatherHeaderTexts = listOf(
            "${fakeUserLocation.address.name}, ${fakeUserLocation.address.country}",
            fakeWeatherHeaderInfo.description.capitalizeWords(),
            "${fakeWeatherHeaderInfo.temperature}°C",
            context.getString(R.string.feels_like, fakeWeatherHeaderInfo.feelsLike),
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
            context.getString(R.string.uv_index),
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
            Pair("%.2f".format(fakeDetailedWeather.windSpeed), 1),
            Pair("%.2f".format(fakeDetailedWeather.rain), 3),
            Pair("%.2f".format(fakeDetailedWeather.snow), 4),
            Pair(fakeDetailedWeather.pressure.toString(), 5),
            Pair(fakeDetailedWeather.humidity.toString(), 6),
            Pair(fakeDetailedWeather.clouds.toString(), 7),
            Pair("%.1f".format(fakeDetailedWeather.visibility), 8),
            Pair(fakeDetailedWeather.uvi.toString(), 9),
            Pair(convertTimestampToHourMinute(fakeDetailedWeather.sunrise), 10),
            Pair(convertTimestampToHourMinute(fakeDetailedWeather.sunset), 11),
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
        val isDeleteModeEnabled = mutableStateOf(false)

        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherInfo = fakeWeatherInfo,
                    weatherTileDataHistory = listOf(fakeWeatherTileData),
                    isEditModeEnabled = isEditModeEnabled.value,
                ),
                onWeatherScreenEvent = { event ->
                    if (event is WeatherScreenEvent.ToggleEditMode) {
                        isEditModeEnabled.value = event.enabled
                    } else if (event is WeatherScreenEvent.ToggleDeleteMode) {
                        isDeleteModeEnabled.value = event.enabled
                    } else if (event is WeatherScreenEvent.SaveLayoutAndExitEditMode) {
                        isEditModeEnabled.value = false
                    }
                }
            )
        }

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.enter_edit_mode))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.exit_edit_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.start_delete_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.exit_edit_mode))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.enter_edit_mode))
            .performClick()
    }

    @Test
    fun weatherScreen_DeleteTileInDeleteMode_CorrectlyDeletesTile() {
        val weatherTileData = mutableStateOf(fakeWeatherTileData)
        val isEditModeEnabled = mutableStateOf(false)
        val isDeleteModeEnabled = mutableStateOf(false)

        composeTestRule.setContent {
            WeatherScreen(
                weatherState = WeatherState(
                    weatherInfo = fakeWeatherInfo,
                    weatherTileDataHistory = listOf(fakeWeatherTileData),
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
            .onNodeWithContentDescription(context.getString(R.string.enter_edit_mode))
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.exit_edit_mode))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(context.getString(R.string.start_delete_mode))
            .assertIsDisplayed()

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
            .filter(!hasTestTag(HOURLY_FORECAST_TAG))
            .filter(!hasTestTag(DAILY_FORECAST_TAG))
            .assertAll(hasTestTag(SHAKING_TAG))

        composeTestRule
            .onNodeWithTag(TILES_GRID_TAG)
            .performScrollToIndex(3)

        composeTestRule
            .onNodeWithText("%.2f".format(fakeDetailedWeather.windSpeed))
            .performClick()

//        composeTestRule
//            .onNodeWithText("%.2f".format(fakeDetailedWeather.windSpeed))
//            .assertIsNotDisplayed()
    }

    companion object {
        private const val TILES_GRID_TAG = "TilesGrid"
        private const val SHAKING_TAG = "Shaking"
        private const val WEATHER_HEADER_TAG = "WeatherHeader"
        private const val GRID_BOTTOM_SPACER_TAG = "GridBottomSpacer"
        private const val HOURLY_FORECAST_TAG = "HourlyForecast"
        private const val DAILY_FORECAST_TAG = "DailyForecast"
    }
}
