package com.example.weatherapp.location_search.presentation.place_search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.consume
import com.example.weatherapp.location_search.presentation.place_search.components.PlaceSearchTextField
import com.example.weatherapp.location_search.presentation.place_search.components.PlaceSuggestionList
import com.example.weatherapp.location_search.presentation.place_search.fake.fakePlaceSuggestions
import com.example.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LocationSearchRoot(
    onNavigateBack: () -> Unit,
    locationSearchViewModel: LocationSearchViewModel = koinViewModel<LocationSearchViewModel>(),
) {
    val locationSearchState by locationSearchViewModel.locationSearchState.collectAsStateWithLifecycle()

    LocationSearchScreen(
        locationSearchState = locationSearchState,
        onLocationSearchScreenEvent = { event ->
            when (event) {
                is LocationSearchScreenEvent.NavigateBack -> onNavigateBack()
                else -> Unit
            }
            locationSearchViewModel.onLocationSearchScreenEvent(event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    locationSearchState: LocationSearchState,
    onLocationSearchScreenEvent: (LocationSearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val topBarBackground = MaterialTheme.colorScheme.surfaceContainer
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val navigateBack = { onLocationSearchScreenEvent(LocationSearchScreenEvent.NavigateBack) }

    locationSearchState.navigateBackEvent?.let {
        LaunchedEffect(it) {
            navigateBack()
            it.consume()
        }
    }

    locationSearchState.showMessageEvent?.let {
        LaunchedEffect(it) {
            snackbarHostState.showSnackbar(it.data.asString(context))
            it.consume()
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarBackground
                ),
                title = {
                    PlaceSearchTextField(
                        value = locationSearchState.locationSearchQuery,
                        enabled = locationSearchState.currentlySavingPlaceId == null,
                        onLeadingIconClick = navigateBack,
                        onSearchClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                        onValueChange = {
                            onLocationSearchScreenEvent(
                                LocationSearchScreenEvent.LocationSearchQueryChanged(it)
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                },
            )
        },
        modifier = modifier
    ) { innerPadding ->
        AnimatedContent(
            targetState = locationSearchState.isLoadingSuggestions,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { isLoading ->
            if (isLoading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(dimensionResource(R.dimen.size_medium))
                    )
                }
            } else {
                PlaceSuggestionList(
                    placeSuggestions = locationSearchState.placeSuggestions,
                    currentlySavingPlaceId = locationSearchState.currentlySavingPlaceId,
                    onPlaceSuggestionClicked = {
                        onLocationSearchScreenEvent(
                            LocationSearchScreenEvent.PlaceSelected(it)
                        )
                    }
                )
            }
        }
    }
}

class IsLoadingSuggestionsParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun LocationSearchScreenPreview(
    @PreviewParameter(IsLoadingSuggestionsParameterProvider::class) isLoadingSuggestions: Boolean,
) {
    WeatherAppTheme {
        LocationSearchScreen(
            locationSearchState = LocationSearchState(
                placeSuggestions = fakePlaceSuggestions,
                isLoadingSuggestions = isLoadingSuggestions
            ),
            onLocationSearchScreenEvent = {},
        )
    }
}