package com.example.weatherapp.weather.presentation.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.components.WeatherCard
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WindTile(
    windSpeed: Double,
    windDirection: Int,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = R.drawable.ic_wind,
        headerText = R.string.wind,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "%.2f".format(windSpeed),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = stringResource(R.string.ms),
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.size_big))
                    .border(1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(dimensionResource(R.dimen.size_big) / 2))
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(R.string.north_abbr),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                Text(
                    text = stringResource(R.string.south_abbr),
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                Text(
                    text = stringResource(R.string.west_abbr),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = stringResource(R.string.east_abbr),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow),
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.size_medium))
                        .rotate(windDirection.toFloat())
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WindTilePreview() {
    WeatherAppTheme {
        WindTile(
            windSpeed = 4.09,
            windDirection = 121
        )
    }
}

