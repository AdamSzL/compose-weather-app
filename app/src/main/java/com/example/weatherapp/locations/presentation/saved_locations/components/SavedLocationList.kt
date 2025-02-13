package com.example.weatherapp.locations.presentation.saved_locations.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SavedLocationList(
    savedLocations: List<SavedLocation>,
    selectedLocation: SavedLocation?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_big))
    ) {
        items(savedLocations) { savedLocation ->
            SavedLocationItem(
                savedLocation = savedLocation,
                isSelected = savedLocation == selectedLocation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

class SelectedLocationParameterProvider: PreviewParameterProvider<SavedLocation?> {
    override val values: Sequence<SavedLocation?>
        get() = sequenceOf(mockSavedLocations.first(), null)
}

@Preview(showBackground = true)
@Composable
private fun SavedLocationListPreview(
    @PreviewParameter(SelectedLocationParameterProvider::class) selectedLocation: SavedLocation
) {
    WeatherAppTheme {
        SavedLocationList(
            savedLocations = mockSavedLocations,
            selectedLocation = selectedLocation
        )
    }
}