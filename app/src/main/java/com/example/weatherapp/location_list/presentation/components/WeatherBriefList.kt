package com.example.weatherapp.location_list.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.fake.fakeLocationWeatherBriefs
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WeatherBriefList(
    locations: List<LocationWeatherBrief>,
    isRefreshingWeatherBriefs: Boolean,
    onNavigateToWeatherScreen: (GeoLocation) -> Unit,
    onLocationDelete: (Long) -> Unit,
    onWeatherBriefRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshingWeatherBriefs,
        onRefresh = onWeatherBriefRefresh,
        indicator = {
            PullToRefreshDefaults.LoadingIndicator(
                state = state,
                isRefreshing = isRefreshingWeatherBriefs,
                modifier = Modifier.align(TopCenter)
            )
        },
        modifier = modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
            modifier = Modifier
                .fillMaxSize()
                .testTag("saved_locations_list")
                .padding(horizontal = dimensionResource(R.dimen.padding_big))
                .padding(
                    bottom = dimensionResource(R.dimen.padding_big),
                    top = dimensionResource(R.dimen.padding_medium)
                )
        ) {
            items(
                items = locations,
                key = { it.location.id }
            ) {
                    locationWeatherBrief ->
                WeatherBriefItem(
                    locationWeatherBrief = locationWeatherBrief,
                    onNavigateToWeatherScreen = onNavigateToWeatherScreen,
                    onDelete = {
                        onLocationDelete(locationWeatherBrief.location.id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(
                            fadeInSpec = tween(durationMillis = 250),
                            fadeOutSpec = tween(durationMillis = 100),
                            placementSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                )
            }
        }
    }
}

@Composable
@Preview
private fun SavedLocationListPreview() {
    WeatherAppTheme {
        WeatherBriefList(
            locations = fakeLocationWeatherBriefs,
            isRefreshingWeatherBriefs = true,
            onNavigateToWeatherScreen = {},
            onLocationDelete = {},
            onWeatherBriefRefresh = {}
        )
    }
}