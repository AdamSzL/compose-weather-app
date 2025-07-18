package com.example.weatherapp.weather.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherCard(
    @DrawableRes headerIcon: Int,
    @StringRes headerText: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big)),
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_big))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(headerIcon),
                    contentDescription = null
                )
                Text(
                    text = stringResource(headerText)
                )
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherCardPreview() {
    WeatherAppTheme {
        WeatherCard(
            headerIcon = R.drawable.ic_twilight_wb,
            headerText = R.string.sunrise_sunset
        ) {
            Text(text = "Content")
        }
    }
}

