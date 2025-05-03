package com.example.weatherapp.location_search.presentation.map.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun LocationMapControls(
    isCancelEnabled: Boolean,
    isSaveEnabled: Boolean,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = onCancel,
            enabled = isCancelEnabled,
        ) {
            Text(
                text = stringResource(R.string.cancel)
            )
        }
        Button(
            onClick = onSave,
            enabled = isSaveEnabled,
        ) {
            Text(
                text = stringResource(R.string.save)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationMapControlsPreview() {
    WeatherAppTheme {
        LocationMapControls(
            isCancelEnabled = true,
            isSaveEnabled = false,
            onCancel = {},
            onSave = {}
        )
    }
}

