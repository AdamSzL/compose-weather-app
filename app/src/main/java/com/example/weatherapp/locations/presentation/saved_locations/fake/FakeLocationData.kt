package com.example.weatherapp.locations.presentation.saved_locations.fake

import com.example.weatherapp.core.domain.model.BriefWeather
import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.GeoLocation
import com.example.weatherapp.core.domain.model.GeoPoint
import com.example.weatherapp.locations.domain.models.LocationWeatherBrief
import java.util.UUID

val fakeLocations = listOf(
    LocationWeatherBrief(
        id = UUID.randomUUID().toString(),
        location = GeoLocation(
            coordinates = GeoPoint(latitude = 40.7128, longitude = -74.0060),
            address = GeoAddress("New York City", "USA")
        ),
        weatherBrief = BriefWeather(
            temperature = 15.1,
            icon = "01d",
            description = "clear sky"
        )
    ),
    LocationWeatherBrief(
        id = UUID.randomUUID().toString(),
        location = GeoLocation(
            coordinates = GeoPoint(latitude = 48.8584, longitude = 2.2945),
            address = GeoAddress("Eiffel Tower", "France")
        ),
        weatherBrief = BriefWeather(
            temperature = 18.5,
            icon = "02d",
            description = "few clouds"
        )
    ),
    LocationWeatherBrief(
        id = UUID.randomUUID().toString(),
        location = GeoLocation(
            coordinates = GeoPoint(latitude = 35.682839, longitude = 139.759455),
            address = GeoAddress("Tokyo", "Japan")
        ),
        weatherBrief = BriefWeather(
            temperature = 22.3,
            icon = "03d",
            description = "scattered clouds"
        )
    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = -33.865143, longitude = 151.209900),
//            address = GeoAddress("Unknown Location", null)
//        ),
//        weatherBrief = BriefWeather(
//            temperature = 20.3,
//            icon = "04d",
//            description = "broken clouds"
//        )
//    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = -22.9068, longitude = -43.1729),
//            address = GeoAddress("Rio de Janeiro", "Brazil")
//        ),
//        weatherBrief = BriefWeather(
//            temperature = 28.3,
//            icon = "10d",
//            description = "rain"
//        )
//    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = 12.3456, longitude = -98.7654),
//            address = GeoAddress("Deserted Island", null)
//        ),
//        weatherBrief = BriefWeather(
//            temperature = 31.3,
//            icon = "11d",
//            description = "thunderstorm"
//        )
//    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = -33.9249, longitude = 18.4241),
//            address = GeoAddress("Cape Town", "South Africa")
//        ),
//        weatherBrief = null
//    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = -33.8568, longitude = 151.2153),
//            address = GeoAddress("Sydney Opera House", "Australia sldkfjosfiosjiofweifjwo")
//        ),
//        weatherBrief = BriefWeather(
//            temperature = 21.7,
//            icon = "50d",
//            description = "mist"
//        )
//    ),
//    LocationWeatherBrief(
//        id = UUID.randomUUID().toString(),
//        location = GeoLocation(
//            coordinates = GeoPoint(latitude = -75.2509, longitude = -0.0714),
//            address = GeoAddress("Antarctica Research Base", null)
//        ),
//        weatherBrief = BriefWeather(
//            temperature = -25.2,
//            icon = "13d",
//            description = "snow"
//        )
//    )
)

val fakeUserLocation = LocationWeatherBrief(
    id = UUID.randomUUID().toString(),
    location = GeoLocation(
        coordinates = GeoPoint(latitude = -13.1631, longitude = -72.5450),
        address = GeoAddress("Machu Picchu", "Peru")
    ),
)