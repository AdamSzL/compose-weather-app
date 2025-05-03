package com.example.weatherapp.core.fake

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.location_list.domain.models.LocationWeatherBrief

val fakeLocations = listOf(
    GeoLocation(
        id = 1,
        coordinates = GeoPoint(latitude = 40.7128, longitude = -74.0060),
        address = GeoAddress("New York City", "USA")
    ),
    GeoLocation(
        id = 2,
        coordinates = GeoPoint(latitude = 48.8584, longitude = 2.2945),
        address = GeoAddress("Eiffel Tower", "France")
    ),
    GeoLocation(
        id = 3,
        coordinates = GeoPoint(latitude = 35.682839, longitude = 139.759455),
        address = GeoAddress("Tokyo", "Japan")
    ),
    GeoLocation(
        id = 4,
        coordinates = GeoPoint(latitude = -33.865143, longitude = 151.209900),
        address = GeoAddress("Unknown Location", null)
    ),
    GeoLocation(
        id = 5,
        coordinates = GeoPoint(latitude = -22.9068, longitude = -43.1729),
        address = GeoAddress("Rio de Janeiro", "Brazil")
    ),
)

val fakeWeatherBriefs = listOf(
    BriefWeather(
        temperature = 15.1,
        icon = "01d",
        description = "clear sky"
    ),
    BriefWeather(
        temperature = 18.5,
        icon = "02d",
        description = "few clouds"
    ),
    BriefWeather(
        temperature = 22.3,
        icon = "03d",
        description = "scattered clouds"
    ),
    BriefWeather(
        temperature = 20.3,
        icon = "04d",
        description = "broken clouds"
    ),
    BriefWeather(
        temperature = 28.3,
        icon = "10d",
        description = "rain"
    )
)

val fakeLocationWeatherBriefs = fakeLocations.mapIndexed { index, location ->
    LocationWeatherBrief(
        location = location,
        weatherBrief = fakeWeatherBriefs[index]
    )
}

val fakeUserLocation = GeoLocation(
    id = 6,
    coordinates = GeoPoint(latitude = -13.1631, longitude = -72.5450),
    address = GeoAddress("Machu Picchu", "Peru")
)