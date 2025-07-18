package com.example.weatherapp.weather.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weather.presentation.components.tiles.CloudinessTile
import com.example.weatherapp.weather.presentation.components.tiles.HumidityTile
import com.example.weatherapp.weather.presentation.components.tiles.PressureTile
import com.example.weatherapp.weather.presentation.components.tiles.RainTile
import com.example.weatherapp.weather.presentation.components.tiles.SnowTile
import com.example.weatherapp.weather.presentation.components.tiles.SunriseTile
import com.example.weatherapp.weather.presentation.components.tiles.SunsetTile
import com.example.weatherapp.weather.presentation.components.tiles.UviTile
import com.example.weatherapp.weather.presentation.components.tiles.VisibilityTile
import com.example.weatherapp.weather.presentation.components.tiles.WindTile
import com.example.weatherapp.weather.presentation.model.WeatherTileData

@Composable
fun WeatherTile(
    tileData: WeatherTileData,
    modifier: Modifier = Modifier,
) {
    when (tileData) {
        is WeatherTileData.Cloudiness -> {
            CloudinessTile(
                cloudiness = tileData.cloudiness,
                modifier = modifier
            )
        }
        is WeatherTileData.Humidity -> {
            HumidityTile(
                humidity = tileData.humidity,
                modifier = modifier
            )
        }
        is WeatherTileData.Pressure -> {
            PressureTile(
                pressure = tileData.pressure,
                modifier = modifier
            )
        }
        is WeatherTileData.Rain -> {
            RainTile(
                rain = tileData.rain,
                modifier = modifier
            )
        }
        is WeatherTileData.Snow -> {
            SnowTile(
                snow = tileData.snow,
                modifier = modifier
            )
        }
        is WeatherTileData.Sunrise -> {
            SunriseTile(
                sunrise = tileData.sunrise,
                modifier = modifier
            )
        }
        is WeatherTileData.Sunset -> {
            SunsetTile(
                sunset = tileData.sunset,
                modifier = modifier
            )
        }
        is WeatherTileData.Visibility -> {
            VisibilityTile(
                visibility = tileData.visibility,
                modifier = modifier
            )
        }
        is WeatherTileData.Wind -> {
            WindTile(
                windSpeed = tileData.windSpeed,
                windDirection = tileData.windDirection,
                modifier = modifier
            )
        }
        is WeatherTileData.Uvi -> {
            UviTile(
                uvi = tileData.uvi,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherTilePreview() {
    WeatherAppTheme {
        WeatherTile(
            tileData = WeatherTileData.Rain(2.23)
        )
    }
}

