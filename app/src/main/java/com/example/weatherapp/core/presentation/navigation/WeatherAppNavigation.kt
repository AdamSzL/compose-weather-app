package com.example.weatherapp.core.presentation.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.weatherapp.locations.presentation.LocationsScreen
import com.example.weatherapp.locations.presentation.LocationsViewModel
import com.example.weatherapp.weather.presentation.forecast.ForecastScreen
import com.example.weatherapp.weather.presentation.forecast.ForecastViewModel
import com.example.weatherapp.weather.presentation.weather.WeatherScreen
import com.example.weatherapp.weather.presentation.weather.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherAppNavigation(
    modifier: Modifier = Modifier
) {
    var currentScreen by rememberSaveable { mutableStateOf(WeatherAppScreen.WeatherScreen) }
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            WeatherAppScreen.entries.forEach { screen ->
                item(
                    selected = screen == currentScreen,
                    onClick = { currentScreen = screen },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (screen == currentScreen) screen.activeIcon else screen.inactiveIcon
                            ),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(screen.label),
                            fontWeight = if (screen == currentScreen) FontWeight.Bold else FontWeight.Normal,
                        )
                    }
                )
            }
        },
        layoutType = customNavSuiteType,
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = currentScreen,
        ) { screen ->
            when (screen) {
                WeatherAppScreen.WeatherScreen -> {
                    val weatherViewModel = koinViewModel<WeatherViewModel>()
                    val weatherState by weatherViewModel.weatherState.collectAsStateWithLifecycle()
                    WeatherScreen(
                        weatherState = weatherState
                    )
                }
                WeatherAppScreen.ForecastScreen -> {
                    val forecastViewModel = koinViewModel<ForecastViewModel>()
                    val forecastState by forecastViewModel.forecastState.collectAsStateWithLifecycle()
                    ForecastScreen(
                        forecastState = forecastState
                    )
                }
                WeatherAppScreen.LocationsScreen -> {
                    val locationsViewModel = koinViewModel<LocationsViewModel>()
                    val locationsState by locationsViewModel.locationsState.collectAsStateWithLifecycle()
                    LocationsScreen(
                        locationsState = locationsState
                    )
                }
            }
        }
    }
}