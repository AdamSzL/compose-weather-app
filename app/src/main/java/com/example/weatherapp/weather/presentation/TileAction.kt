package com.example.weatherapp.weather.presentation

sealed interface TileAction {
    data class Move(val from: Int, val to: Int): TileAction
    data class Delete(val tileId: String): TileAction
    data object Shuffle: TileAction
    data class JumpToHistoryIndex(val index: Int): TileAction
}