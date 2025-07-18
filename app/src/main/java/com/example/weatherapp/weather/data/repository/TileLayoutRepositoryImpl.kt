package com.example.weatherapp.weather.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.weatherapp.weather.data.mapper.type
import com.example.weatherapp.weather.data.model.WeatherTileType
import com.example.weatherapp.weather.presentation.model.WeatherTileData
import kotlinx.coroutines.flow.first

class TileLayoutRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): TileLayoutRepository {

    override suspend fun loadTileOrder(): List<WeatherTileType> {
        val prefs = dataStore.data.first()
        val orderString = prefs[TILE_ORDER_KEY] ?: return emptyList()
        return orderString.split(",").mapNotNull { runCatching { WeatherTileType.valueOf(it) }.getOrNull() }
    }

    override suspend fun saveTileOrder(tiles: List<WeatherTileData>) {
        val typeList = tiles.map { it.type().name }
        val serialized = typeList.joinToString(",")

        dataStore.edit { prefs ->
            prefs[TILE_ORDER_KEY] = serialized
        }
    }

    override suspend fun resetTileOrder() {
        dataStore.edit { prefs ->
            prefs.remove(TILE_ORDER_KEY)
        }
    }

    companion object {
        val TILE_ORDER_KEY = stringPreferencesKey("tile_order")
    }
}