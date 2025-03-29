package com.example.weatherapp.locations.presentation.saved_locations.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.example.weatherapp.R
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.formattedAddress
import com.example.weatherapp.core.presentation.components.ConfirmAlertDialog
import com.example.weatherapp.core.presentation.utils.weatherIconUrl
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.weather.utils.capitalizeWords

@OptIn(ExperimentalCoilApi::class)
@Composable
fun WeatherBriefItem(
    locationWeatherBrief: LocationWeatherBrief,
    onNavigateToWeatherScreen: (location: GeoLocation) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }
    var isDeleteLocationDialogVisible by remember { mutableStateOf(false) }
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> {
                    onDelete()
                    true
                }
                else -> {
                    false
                }
            }
        },
    )
    val fullLocationName by remember(locationWeatherBrief.location.address) {
        mutableStateOf(locationWeatherBrief.location.address.formattedAddress())
    }

    if (isDeleteLocationDialogVisible) {
        ConfirmAlertDialog(
            title = stringResource(R.string.delete_location),
            text = stringResource(R.string.delete_location_confirmation, fullLocationName),
            icon = Icons.Default.Delete,
            onDismissRequest = {
                isDeleteLocationDialogVisible = !isDeleteLocationDialogVisible
            },
            onConfirmation = onDelete,
        )
    }

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(dimensionResource(R.dimen.size_small))
                    )
                    .background(MaterialTheme.colorScheme.errorContainer)
            ) {
                DeleteIcon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = dimensionResource(R.dimen.padding_big))
                )
                DeleteIcon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = -dimensionResource(R.dimen.padding_big))
                )
            }
        },
        modifier = modifier
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(dimensionResource(R.dimen.size_small)),
            onClick = {
                onNavigateToWeatherScreen(locationWeatherBrief.location)
            },
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Max)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_big),
                    )
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    AnimatedContent(
                        targetState = locationWeatherBrief.weatherBrief != null
                    ) { weatherBriefAvailable ->
                        if (weatherBriefAvailable) {
                            CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
                                AsyncImage(
                                    model = weatherIconUrl.format(locationWeatherBrief.weatherBrief!!.icon, "2"),
                                    contentDescription = null,
                                    modifier = Modifier.size(dimensionResource(R.dimen.size_medium)),
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.ic_dash),
                                contentDescription = stringResource(R.string.weather_details_missing),
                                modifier = Modifier.size(dimensionResource(R.dimen.size_medium))
                            )
                        }
                    }
                    Column {
                        Text(
                            text = fullLocationName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        AnimatedContent(
                            targetState = locationWeatherBrief.weatherBrief != null
                        ) { weatherBriefAvailable ->
                            Text(
                                text = if (weatherBriefAvailable) {
                                    locationWeatherBrief.weatherBrief!!.description.capitalizeWords()
                                } else {
                                    stringResource(R.string.no_data)
                                },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                AnimatedContent(
                    targetState = locationWeatherBrief.weatherBrief != null
                ) { weatherBriefAvailable ->
                    if (weatherBriefAvailable) {
                        Text(
                            text = stringResource(R.string.degrees, locationWeatherBrief.weatherBrief!!.temperature.toInt()),
                            style = MaterialTheme.typography.displaySmall,
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_dash),
                            contentDescription = stringResource(R.string.temperature_missing),
                            modifier = Modifier.size(dimensionResource(R.dimen.size_medium))
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SavedLocationItemPreview() {
    WeatherAppTheme {
        WeatherBriefItem(
            locationWeatherBrief = fakeLocations.first(),
            onNavigateToWeatherScreen = {},
            onDelete = {}
        )
    }
}