package com.example.weatherapp.locations.presentation.saved_locations.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Ro
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.ConfirmAlertDialog
import com.example.weatherapp.locations.domain.LocationSource
import com.example.weatherapp.locations.domain.SavedLocation
import com.example.weatherapp.locations.presentation.saved_locations.mock.mockSavedLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.delay

@Composable
fun SavedLocationItem(
    savedLocation: SavedLocation,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRemoved by remember { mutableStateOf(false) }
    var isDeleteLocationDialogVisible by remember { mutableStateOf(false) }
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    onSelect()
                    false
                }
                else -> {
                    false
                }
            }
        },
        positionalThreshold = { totalDistance ->
            0.1f * totalDistance
        }
    )
    val animatedCardBackgroundColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else CardDefaults.elevatedCardColors().containerColor,
        label = "color"
    )
    val fullLocationName = if (savedLocation.country != null) "${savedLocation.name}, ${savedLocation.country}" else savedLocation.name

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(500)
            onDelete()
        }
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

    AnimatedVisibility(
        visible = !isRemoved,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top,
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top,
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swipeToDismissState,
            backgroundContent = {
                val color by animateColorAsState(
                    when (swipeToDismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surfaceContainer
                        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primaryContainer
                        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(R.dimen.padding_big))
                        .clip(
                            RoundedCornerShape(dimensionResource(R.dimen.size_small))
                        )
                        .background(color)
                ) {
                    if (swipeToDismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_location),
                            modifier = Modifier.align(Alignment.CenterEnd).offset(x = -dimensionResource(R.dimen.padding_big)),
                        )
                    } else if (swipeToDismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.set_as_active),
                            modifier = Modifier.align(Alignment.CenterStart).offset(x = dimensionResource(R.dimen.padding_big)),
                        )
                    }
                }
            }
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(dimensionResource(R.dimen.size_small)),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = animatedCardBackgroundColor,
                ),
                onClick = onSelect,
                modifier = modifier
                    .padding(top = dimensionResource(R.dimen.padding_big))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_big),
                            vertical = dimensionResource(R.dimen.padding_medium)
                        )
                ) {
                    Row {
                        AnimatedVisibility(
                            visible = isSelected,
                            enter = expandHorizontally(
                                animationSpec = tween(500),
                            ),
                            exit = shrinkHorizontally(
                                shrinkTowards = Alignment.Start,
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.selected_location),
                                modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                            )
                        }
                        Text(
                            text = fullLocationName,
                        )
                    }
                    IconButton(
                        onClick = {
                            isDeleteLocationDialogVisible = true
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete_location)
                        )
                    }
                }
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
            onSelect = {},
            onDelete = {}
        )
    }
}