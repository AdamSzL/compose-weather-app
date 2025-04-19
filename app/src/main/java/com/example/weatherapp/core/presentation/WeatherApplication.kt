package com.example.weatherapp.core.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.core.presentation.navigation.WeatherAppScreen
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchScreen
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchScreenEvent
import com.example.weatherapp.location_search.presentation.place_search.LocationSearchViewModel
import com.example.weatherapp.location_search.presentation.map.LocationMapScreen
import com.example.weatherapp.location_list.presentation.LocationsScreen
import com.example.weatherapp.location_list.presentation.LocationListScreenEvent
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.forecast.presentation.ForecastScreen
import com.example.weatherapp.forecast.presentation.ForecastViewModel
import com.example.weatherapp.weather.presentation.WeatherScreen
import com.example.weatherapp.weather.presentation.WeatherScreenEvent
import com.example.weatherapp.weather.presentation.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.typeOf

@Composable
fun WeatherApplication(
    onGoToAppSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherAppScreen.LocationsScreen,
        popExitTransition = {
            scaleOut(
                targetScale = 0.9f,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
            )
        },
        popEnterTransition = {
            EnterTransition.None
        },
        modifier = modifier
    ) {
        composable<WeatherAppScreen.WeatherScreen>(
            typeMap = mapOf(
                typeOf<GeoLocation>() to CustomNavType.GeoLocationType
            )
        ) { backStackEntry ->
            val location = backStackEntry.toRoute<WeatherAppScreen.WeatherScreen>().location
            val weatherViewModel = koinViewModel<WeatherViewModel>(
                parameters = { parametersOf(location) }
            )
            val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()
            WeatherScreen(
                location = location,
                weatherState = weatherState,
                onWeatherScreenEvent = {
                    when (it) {
                        is WeatherScreenEvent.NavigateBack -> navController.popBackStack()
                        else -> Unit
                    }
                    weatherViewModel.onWeatherScreenEvent(it)
                }
            )
        }
        composable<WeatherAppScreen.ForecastScreen> {
            val forecastViewModel = koinViewModel<ForecastViewModel>()
            val forecastState by forecastViewModel.forecastState.collectAsStateWithLifecycle()
            ForecastScreen(
                forecastState = forecastState
            )
        }
        composable<WeatherAppScreen.LocationsScreen> { backStackEntry ->
            val locationListViewModel = koinViewModel<LocationListViewModel>()
            val locationsState by locationListViewModel.locationsState.collectAsStateWithLifecycle()
            val selectedMapLocationLatLng = backStackEntry.savedStateHandle.get<Pair<Double, Double>>("selected_location")
            val selectedMapLocation = if (selectedMapLocationLatLng != null) {
                GeoPoint(selectedMapLocationLatLng.first, selectedMapLocationLatLng.second)
            } else null
            LocationsScreen(
                locationListState = locationsState,
                selectedMapLocation = selectedMapLocation,
                onLocationScreenEvent = {
                    when (it) {
                        LocationListScreenEvent.NavigateToLocationMap -> navController.navigate(WeatherAppScreen.LocationMapScreen)
                        LocationListScreenEvent.NavigateToLocationSearch -> navController.navigate(WeatherAppScreen.LocationSearchScreen)
                        is LocationListScreenEvent.NavigateToWeatherScreen -> navController.navigate(WeatherAppScreen.WeatherScreen(it.location))
                        LocationListScreenEvent.GoToAppSettings -> onGoToAppSettings()
                        LocationListScreenEvent.ResetSavedMapLocation -> backStackEntry.savedStateHandle.remove<Pair<Double, Double>>("selected_location")
                        else -> Unit
                    }
                    locationListViewModel.onLocationsScreenEvent(it)
                }
            )
        }
        composable<WeatherAppScreen.LocationMapScreen> {
            LocationMapScreen(
                onLocationSelected = { latLng ->
                    val latitudeLongitude = Pair(latLng.longitude, latLng.latitude)
                    navController.previousBackStackEntry?.savedStateHandle?.set("selected_location", latitudeLongitude)
                    navController.popBackStack()
                },
                onCancelLocationSelection = {
                    navController.popBackStack()
                },
            )
        }
        composable<WeatherAppScreen.LocationSearchScreen> {
            val locationSearchViewModel = koinViewModel<LocationSearchViewModel>()
            val locationSearchState by locationSearchViewModel.locationSearchState.collectAsStateWithLifecycle()
            LocationSearchScreen(
                locationSearchState = locationSearchState,
                onLocationSearchScreenEvent = { event ->
                    when (event) {
                        is LocationSearchScreenEvent.NavigateBack -> {
                            navController.popBackStack()
                        }
                        else -> Unit
                    }
                    locationSearchViewModel.onLocationSearchScreenEvent(event)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherApplicationPreview() {
    WeatherAppTheme {
        WeatherApplication(
            onGoToAppSettings = {}
        )
    }
}

