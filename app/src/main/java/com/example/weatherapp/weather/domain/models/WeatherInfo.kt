package com.example.weatherapp.weather.domain.models

data class WeatherInfo(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val visibility: Int,
    val rain: Double,
    val snow: Double,
    val cloudiness: Int,
    val weatherCondition: String,
    val weatherDescription: String,
    val weatherIcon: String,
    val sunrise: Long,
    val sunset: Long
)

fun WeatherInfo.toWeatherHeaderInfo(): WeatherHeaderInfo {
    return WeatherHeaderInfo(
        cityName = cityName,
        country = country,
        temperature = temperature,
        feelsLike = feelsLike,
        minTemperature = minTemperature,
        maxTemperature = maxTemperature,
        weatherCondition = weatherCondition,
        weatherDescription = weatherDescription,
        weatherIcon = weatherIcon
    )
}

fun WeatherInfo.toWeatherTileDataList(): List<WeatherTileData> {
    return listOf(
        WeatherTileData.WindSpeed(windSpeed = windSpeed),
        WeatherTileData.WindDirection(windDirection = windDirection),
        WeatherTileData.Rain(rain = rain),
        WeatherTileData.Snow(snow = snow),
        WeatherTileData.Pressure(pressure = pressure),
        WeatherTileData.Humidity(humidity = humidity),
        WeatherTileData.Cloudiness(cloudiness = cloudiness),
        WeatherTileData.Visibility(visibility = visibility),
        WeatherTileData.Sunrise(sunrise = sunrise),
        WeatherTileData.Sunset(sunset = sunset)
    )
}
