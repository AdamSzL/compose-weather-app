package com.example.weatherapp.weather.presentation.weather

sealed class WeatherScreenEvent {

    data class ToggleEditMode(val enabled: Boolean): WeatherScreenEvent()

    data class ToggleDeleteMode(val enabled: Boolean): WeatherScreenEvent()

    data class ToggleTilesLock(val locked: Boolean): WeatherScreenEvent()

    data class MoveTile(val from: Int, val to: Int): WeatherScreenEvent()

    data class DeleteTile(val tileId: String): WeatherScreenEvent()

    data object ShuffleTiles: WeatherScreenEvent()

    data class ToggleAutoSave(val checked: Boolean): WeatherScreenEvent()

    data object SaveLayout: WeatherScreenEvent()

    data object ResetLayout: WeatherScreenEvent()

    data object UndoLayoutChange: WeatherScreenEvent()

    data object RedoLayoutChange: WeatherScreenEvent()

    data object SaveLayoutAndExitEditMode: WeatherScreenEvent()

}