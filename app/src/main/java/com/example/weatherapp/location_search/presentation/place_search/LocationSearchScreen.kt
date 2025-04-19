package com.example.weatherapp.location_search.presentation.place_search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.location_search.presentation.place_search.components.PlaceSearchTextField
import com.example.weatherapp.location_search.presentation.place_search.components.PlaceSuggestionList
import com.example.weatherapp.location_search.presentation.place_search.fake.fakePlaceSuggestions
import com.example.weatherapp.ui.theme.WeatherAppTheme

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
                        locationSearchState.locationSearchQuery,
                        onLeadingIconClick = {
                            onLocationSearchScreenEvent(LocationSearchScreenEvent.NavigateBack)
                        },
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