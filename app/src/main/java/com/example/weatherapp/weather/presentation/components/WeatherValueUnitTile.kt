package com.example.weatherapp.weather.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherValueUnitTile(
    @DrawableRes tileHeaderIcon: Int,
    @StringRes tileHeaderText: Int,
    value: String,
    modifier: Modifier = Modifier,
    @StringRes unit: Int? = null,
) {
    WeatherCard(
        headerIcon = tileHeaderIcon,
        headerText = tileHeaderText,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.tertiary
                )
                unit?.let {
                    Text(
                        text = stringResource(unit),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

class UnitParameterProvider: PreviewParameterProvider<Int?> {
    override val values: Sequence<Int?>
        get() = sequenceOf(R.string.mmh, null)
}

@Preview(showBackground = true)
@Composable
private fun WeatherValueUnitTilePreview(
    @PreviewParameter(UnitParameterProvider::class) unit: Int?
) {
    WeatherAppTheme {
        WeatherValueUnitTile(
            tileHeaderIcon = R.drawable.ic_pressure,
            tileHeaderText = R.string.pressure,
            value = "1021",
            unit = unit
        )
    }
}