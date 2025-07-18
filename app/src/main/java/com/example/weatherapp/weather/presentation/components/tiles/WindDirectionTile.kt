package com.example.weatherapp.weather.presentation.components.tiles

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.WeatherCard
import com.example.weatherapp.weather.presentation.utils.getWindDirectionText
import com.example.weatherapp.weather.presentation.utils.toUiText

@Composable
fun WindDirectionTile(
    windDirection: Int,
    modifier: Modifier = Modifier
) {
    WeatherCard(
        headerIcon = R.drawable.ic_compass,
        headerText = R.string.wind_direction,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.size_big))
                    .border(
                        1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(
                            dimensionResource(R.dimen.size_big) / 2
                        )
                    )
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
            Text(
                text = getWindDirectionText(windDirection).toUiText().asString(),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WindDirectionTilePreview() {
    WeatherAppTheme {
        WindDirectionTile(
            windDirection = 121
        )
    }
}

