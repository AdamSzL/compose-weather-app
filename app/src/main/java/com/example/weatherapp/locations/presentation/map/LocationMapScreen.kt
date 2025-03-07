package com.example.weatherapp.locations.presentation.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationMapScreen(
    onCancelLocationSelection: () -> Unit,
    onLocationSelected: (LatLng) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLocation by remember { mutableStateOf< LatLng?>(null) }
    val markerState = remember {
        derivedStateOf {
            MarkerState(position = selectedLocation ?: LatLng(0.0, 0.0))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onCancelLocationSelection
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.pick_from_map)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                ),
                modifier = Modifier.background(Color.Transparent)
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPadding)
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                onMapClick = { latLng ->
                    selectedLocation = latLng
                }
            ) {
                selectedLocation?.let {
                    Marker(
                        state = markerState.value,
                        title = stringResource(R.string.selected_location)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dimensionResource(R.dimen.size_medium))
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.size_small)))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium), 
                        vertical = dimensionResource(R.dimen.padding_small))
            ) {
                OutlinedButton(
                    onClick = onCancelLocationSelection,
                ) {
                    Text(
                        text = stringResource(R.string.cancel)
                    )
                }
                Button(
                    onClick = {
                        onLocationSelected(markerState.value.position)
                    },
                    enabled = selectedLocation != null,
                ) {
                    Text(
                        text = stringResource(R.string.select)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@PreviewLightDark
@Composable
private fun MapScreenPreview() {
    WeatherAppTheme {
        LocationMapScreen(
            onCancelLocationSelection = {},
            onLocationSelected = {}
        )
    }
}

