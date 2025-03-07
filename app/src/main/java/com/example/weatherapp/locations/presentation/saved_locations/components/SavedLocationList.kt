package com.example.weatherapp.locations.presentation.saved_locations.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    selectedLocationId: String?,
    onSetLocationAsActive: (String) -> Unit,
    onLocationDelete: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_big))
            .padding(bottom = dimensionResource(R.dimen.padding_big))
    ) {
        items(
            items = savedLocations,
            key = { it.id }
        ) {
            savedLocation ->
            SavedLocationItem(
                savedLocation = savedLocation,
                isSelected = savedLocation.id == selectedLocationId,
                onSelect = {
                    onSetLocationAsActive(savedLocation.id)
                },
                onDelete = {
                    onLocationDelete(savedLocation.id)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

class SelectedLocationIdParameterProvider: PreviewParameterProvider<String?> {
    override val values: Sequence<String?>
        get() = sequenceOf(mockSavedLocations.first().id, null)
}

@Preview(showBackground = true)
@Composable
private fun SavedLocationListPreview(
    @PreviewParameter(SelectedLocationIdParameterProvider::class) selectedLocationId: String?
) {
    WeatherAppTheme {
        SavedLocationList(
            savedLocations = mockSavedLocations,
            selectedLocationId = selectedLocationId,
            onSetLocationAsActive = {},
            onLocationDelete = {}
        )
    }
}