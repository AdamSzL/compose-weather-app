package com.example.weatherapp.core.presentation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.weatherapp.core.domain.model.GeoLocation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val GeoLocationType = object : NavType<GeoLocation>(
        isNullableAllowed = false
    ) {

        override fun get(bundle: Bundle, key: String): GeoLocation? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): GeoLocation {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: GeoLocation): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: GeoLocation) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}