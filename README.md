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
- Full dark mode support
- Fake data fallback for development and UI previews

🚧 **In Progress / Planned:**
- "My location" support (location permissions integration – not fully working yet)
- Search for location by name (e.g., city or place)
- Daily and hourly weather forecasts per location
- Offline support via local weather data caching

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
- Room (caching, planned)
- Fused Location Provider ("My Location", planned)
- Places API ("Search For Location", planned)

### Testing
- Unit tests for ViewModels and utility functions using JUnit
- UI tests for Jetpack Compose (Compose Testing APIs)

## 📸 Screenshots

### Locations

<img src="https://github.com/user-attachments/assets/737878df-9cd7-43aa-b198-569e2987a891" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/ab3a26aa-8834-43e0-9e6a-1953c2c42c45" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/e97ed036-888e-47f6-8a4d-dc3f26bf7d3d" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/1cf0c958-af96-4ef2-a297-852ecd80e434" alt="App screenshot 1" width="300" />

### Choosing a location from map (Maps SDK for Android)
<img src="https://github.com/user-attachments/assets/df6defa2-7dfe-46d6-9d32-53dcc687ebca" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/9b783585-9238-4487-a270-255f68a5d87b" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/c1cc9443-ebf5-4645-a0c0-0cb04cce9386" alt="App screenshot 1" width="300" />

### Viewing detailed weather
<img src="https://github.com/user-attachments/assets/d9370fd5-ba07-40ec-a940-69cca5042d15" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/98c59932-0d3e-4ac5-a6ed-218e7f8b1cec" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/93a14415-4bef-4653-aace-39f11e43095a" alt="App screenshot 1" width="300" />

### Editing detailed weather layout - shuffling tiles, deleting tiles, moving tiles
<img src="https://github.com/user-attachments/assets/5b0c3584-96ea-408c-9674-954bafe93ccc" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/d390c006-dada-4119-bd28-42d20e48d93f" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/ff35c87b-78c1-4342-b36a-1a7fd81ee5a0" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/7c9c600e-2a7c-486f-90e0-a7e61e685e9d" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/751adfbc-294c-4528-9e83-346bfc3a44ba" alt="App screenshot 1" width="300" />
<img src="https://github.com/user-attachments/assets/25e2aa5f-0f75-43cf-b60e-fea74bda738c" alt="App screenshot 1" width="300" />


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
