package com.example.weatherapp.locations.presentation.saved_locations

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.locations.presentation.components.AddLocationFloatingActionButton
import com.example.weatherapp.locations.presentation.saved_locations.components.SavedLocationList
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.maps.android.compose.GoogleMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationsState: LocationsState,
    onNavigateToLocationMap: () -> Unit,
    onNavigateToLocationSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFabExpanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.saved_Locations)
                    )
                }
            )
        },
        floatingActionButton = {
            AddLocationFloatingActionButton(
                isFabExpanded = isFabExpanded,
                onExpandedToggle = {
                    isFabExpanded = !isFabExpanded
                },
                onNavigateToLocationMap = onNavigateToLocationMap,
                onNavigateToLocationSearch = onNavigateToLocationSearch
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SavedLocationList(
                savedLocations = locationsState.savedLocations,
                selectedLocation = locationsState.selectedLocation,
                modifier = Modifier.fillMaxSize()
                    .padding(top = dimensionResource(R.dimen.padding_medium))
            )

            if (isFabExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable { isFabExpanded = false }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationsScreenPreview() {
    WeatherAppTheme {
        LocationsScreen(
            locationsState = LocationsState(
                savedLocations = mockSavedLocations,
                selectedLocation = mockSavedLocations.first(),
            ),
            onNavigateToLocationMap = {},
            onNavigateToLocationSearch = {}
        )
    }
}

