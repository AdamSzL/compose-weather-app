# 🌤️ Compose Weather App

A modern weather application built entirely using Jetpack Compose.  
It supports multiple locations, interactive tile layout editing, and real-time weather data powered by the OpenWeatherMap API.

⚠️ **This app is still in development.** Some features may be incomplete or subject to change.

## ✨ Features

✅ **Implemented:**
- Add weather locations manually or by selecting a point on the map (Maps SDK)
- Real-time current weather data for each location (temperature, conditions, etc.)
- Modular weather tile system: wind speed, humidity, rain, sunrise/sunset, etc.
- Customizable tile layout: move, delete, shuffle, reset, save changes
- Search for location by name (e.g., city or place)
- "My location" support (with location permissions)
- Daily and hourly weather forecasts per location

📝 **Planned:**
- Home screen widget showing current weather and forecast
- WearOS companion app with key weather data
- Notifications with daily weather summary and severe weather alerts

## 🔧 Tech Stack

### Architecture
- MVI (Model-View-Intent)
- Clean Architecture (data / domain / presentation layers)

### Tools & libraries
- Jetpack Compose
- OpenWeatherMap API
- Maps SDK for Android
- Geocoder API
- Koin for Dependency Injection
- Ktor Client for network requests
- Room (caching)
- Fused Location Provider ("My Location")
- Places API ("Search For Location")

### Testing
- Unit tests for ViewModels and utility functions using JUnit
- UI tests for Jetpack Compose (Compose Testing APIs)

## 🎥 Demos

### Adding and removing locations
- You can add a location in several ways: using the current user's location, selecting a place on the map, or searching via the Places API.
- Locations can be removed by swiping left or right.

https://github.com/user-attachments/assets/611d8626-8232-4ce7-bbd3-4923168f3dc3

### Viewing detailed weather for the selected location
- Users can view detailed weather for a location by clicking on it.
- Detailed weather includes daily and hourly forecasts, as well as current data such as temperature, humidity, and more.

https://github.com/user-attachments/assets/14f9f189-8d36-4211-af0a-dd647b421c93

### Editing the tile layout
- Users can freely customize the tile layout by moving and reordering tiles.
- The layout is saved locally and shared across all locations.
- Available actions include shuffling tiles, undoing/redoing changes, restoring the default layout (with all tiles), locking tiles to prevent accidental moves, and deleting tiles.

https://github.com/user-attachments/assets/170be0e7-2acf-4f5d-ab92-067fe4762ccd

## 📦 Project Status

This project is actively in progress.
I'm still adding features and improving architecture — feel free to follow updates or contribute!

## ▶️ How to Run

1. **Clone the repository** and open it in **Android Studio**.
2. In the root of your project, create a `secrets.properties` file with the following contents:

```properties
MAPS_API_KEY=your_maps_sdk_api_key
WEATHER_API_KEY=your_openweathermap_api_key
```

3. Build and run the app on a device or emulator.

⚠️ You need valid API keys from:
- Google Maps SDK for Android
- OpenWeatherMap API



---

> 💡 Check out more of my projects at [szlosarczyk.dev](https://szlosarczyk.dev)
