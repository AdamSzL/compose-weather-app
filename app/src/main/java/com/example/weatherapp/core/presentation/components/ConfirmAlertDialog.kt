package com.example.weatherapp.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ConfirmAlertDialog(
    title: String,
    text: String,
    icon: ImageVector,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        title = {
            Text(
                text = title,
            )
        },
        text = {
            Text(
                text = text,
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(
                    text = stringResource(R.string.confirm)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = stringResource(R.string.cancel)
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ConfirmAlertDialogPreview() {
    WeatherAppTheme {
        ConfirmAlertDialog(
            title = stringResource(R.string.delete_location),
            text = stringResource(R.string.delete_location_confirmation, fakeLocations.first().location.address.name),
            icon = Icons.Default.Delete,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}

