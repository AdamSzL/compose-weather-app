package com.example.weatherapp.location_list.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun DeleteIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Delete,
        tint = MaterialTheme.colorScheme.error,
        contentDescription = stringResource(R.string.delete_location),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun DeleteIconPreview() {
    WeatherAppTheme {
        DeleteIcon()
    }
}

