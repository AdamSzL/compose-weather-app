package com.example.weatherapp.core.presentation.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.locations.presentation.location_search.LocationSearchScreen
import com.example.weatherapp.locations.presentation.map.LocationMapScreen
import com.example.weatherapp.locations.presentation.saved_locations.LocationsScreen
import com.example.weatherapp.locations.presentation.saved_locations.LocationsViewModel
import com.example.weatherapp.weather.presentation.forecast.ForecastScreen
import com.example.weatherapp.weather.presentation.forecast.ForecastViewModel
import com.example.weatherapp.weather.presentation.weather.WeatherScreen
import com.example.weatherapp.weather.presentation.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherAppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = WeatherAppScreen.fromRoute(currentRoute)
    val navigationScreens = listOf(WeatherAppScreen.WeatherScreen, WeatherAppScreen.ForecastScreen, WeatherAppScreen.LocationsScreen)

    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (currentScreen !in navigationScreens) {
            NavigationSuiteType.None
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            if (currentScreen in navigationScreens) {
                navigationScreens.forEach { screen ->
                    item(
                        selected = screen == currentScreen,
                        onClick = {
                            navController.navigate(screen)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(
                                    if (screen == currentScreen) screen.activeIcon!! else screen.inactiveIcon!!
                                ),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.label!!),
                                fontWeight = if (screen == currentScreen) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    )
                }
            }
        },
        layoutType = customNavSuiteType,
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = WeatherAppScreen.WeatherScreen,
            modifier = Modifier
        ) {
            composable<WeatherAppScreen.WeatherScreen> {
                val weatherViewModel = koinViewModel<WeatherViewModel>()
                val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()
                WeatherScreen(
                    weatherState = weatherState
                )
            }
            composable<WeatherAppScreen.ForecastScreen> {
                val forecastViewModel = koinViewModel<ForecastViewModel>()
                val forecastState by forecastViewModel.forecastState.collectAsStateWithLifecycle()
                ForecastScreen(
                    forecastState = forecastState
                )
            }
            composable<WeatherAppScreen.LocationsScreen> {
                val locationsViewModel = koinViewModel<LocationsViewModel>()
                val locationsState by locationsViewModel.locationsState.collectAsStateWithLifecycle()
                LocationsScreen(
                    locationsState = locationsState,
                    onNavigateToLocationMap = {
                        navController.navigate(WeatherAppScreen.LocationMapScreen)
                    },
                    onNavigateToLocationSearch = {
                        navController.navigate(WeatherAppScreen.LocationSearchScreen)
                    }
                )
            }
            composable<WeatherAppScreen.LocationMapScreen> {
                LocationMapScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                )
            }
            composable<WeatherAppScreen.LocationSearchScreen> {
                LocationSearchScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}