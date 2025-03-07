package com.example.weatherapp.locations.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun AddLocationFloatingActionButton(
    isFabExpanded: Boolean,
    onExpandedToggle: () -> Unit,
    onFetchUserLocation: () -> Unit,
    onNavigateToLocationMap: () -> Unit,
    onNavigateToLocationSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedRotation by animateFloatAsState(
        targetValue = if (isFabExpanded) 45f else 0f,
        label = "rotation"
    )
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        LocationTypeBlock(
            visible = isFabExpanded,
            icon = R.drawable.ic_my_location,
            text = R.string.my_location,
            onClick = onFetchUserLocation
        )
        LocationTypeBlock(
            visible = isFabExpanded,
            icon = R.drawable.ic_map,
            text = R.string.pick_from_map,
            onClick = onNavigateToLocationMap
        )
        LocationTypeBlock(
            visible = isFabExpanded,
            icon = R.drawable.ic_search,
            text = R.string.search_for_location,
            onClick = onNavigateToLocationSearch
        )
        ExtendedFloatingActionButton(
            onClick = onExpandedToggle,
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.rotate(animatedRotation)
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.new_location)
                )
            },
        )
    }
}

class IsFabExpandedParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun AddLocationFloatingActionButtonPreview(
    @PreviewParameter(IsFabExpandedParameterProvider::class) isFabExpanded: Boolean
) {
    WeatherAppTheme {
        AddLocationFloatingActionButton(
            isFabExpanded = isFabExpanded,
            onExpandedToggle = {},
            onFetchUserLocation = {},
            onNavigateToLocationMap = {},
            onNavigateToLocationSearch = {}
        )
    }
}