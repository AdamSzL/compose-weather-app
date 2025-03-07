package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun EditModeFloatingActionButton(
    isDeleting: Boolean,
    onToggleDeleteMode: (Boolean) -> Unit,
    onSaveLayoutAndExitEditingMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
    ) {
        AnimatedContent(
            targetState = isDeleting
        ) { isDeleteModeEnabled ->
            FloatingActionButton(
                onClick = {
                    onToggleDeleteMode(!isDeleteModeEnabled)
                },
                containerColor = MaterialTheme.colorScheme.error,
            ) {
                Icon(
                    imageVector = if (isDeleteModeEnabled) Icons.Default.Close else Icons.Default.Delete,
                    contentDescription = stringResource(if (isDeleteModeEnabled) R.string.exit_delete_mode else R.string.start_delete_mode)
                )
            }
        }
        ExtendedFloatingActionButton(
            onClick = onSaveLayoutAndExitEditingMode,
            icon = {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.done)
                )
            },
        )
    }
}

class IsDeletingParameterProvider: PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(true, false)
}

@Preview(showBackground = true)
@Composable
private fun EditModeFloatingActionButtonPreview(
    @PreviewParameter(IsDeletingParameterProvider::class) isDeleting: Boolean
) {
    WeatherAppTheme {
        EditModeFloatingActionButton(
            isDeleting = isDeleting,
            onToggleDeleteMode = {},
            onSaveLayoutAndExitEditingMode = {}
        )
    }
}
