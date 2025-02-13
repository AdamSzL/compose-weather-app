package com.example.weatherapp.locations.presentation.saved_locations.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Ro
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.locations.domain.LocationSource
import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun SavedLocationItem(
    savedLocation: SavedLocation,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = RoundedCornerShape(dimensionResource(R.dimen.size_small)),
        modifier = modifier
            .then(
                if (isSelected) {
                    Modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(dimensionResource(R.dimen.size_small)))
                } else {
                    Modifier
                }
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_big))
        ) {
            Text(
                text = savedLocation.name,
                modifier = Modifier
            )
            if (savedLocation.country != null) {

            }
            if (savedLocation.source == LocationSource.MAP) {
                Icon(
                    painter = painterResource(R.drawable.ic_map),
                    contentDescription = stringResource(R.string.map)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_my_location),
                    contentDescription = stringResource(R.string.gps)
                )
            }
        }
    }
}

class IsSelectedParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun SavedLocationItemPreview(
    @PreviewParameter(IsSelectedParameterProvider::class) isSelected: Boolean
) {
    WeatherAppTheme {
        SavedLocationItem(
            savedLocation = mockSavedLocations.first(),
            isSelected = isSelected,
        )
    }
}