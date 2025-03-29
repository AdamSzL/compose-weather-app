package com.example.weatherapp.locations.domain.models

import com.example.weatherapp.core.domain.model.GeoAddress
import com.example.weatherapp.core.domain.model.formattedAddress
import org.junit.Assert.*
import org.junit.Test

class GeoAddressTest {

    @Test
    fun formattedAddress_AddressWithNameAndCountry_ReturnsAddressInCorrectFormat() {
        val geoAddress = GeoAddress("New York", "USA")
        assertEquals("New York, USA", geoAddress.formattedAddress())
    }

    @Test
    fun formattedAddress_AddressWithCountryNull_ReturnsAddressInCorrectFormat() {
        val geoAddress = GeoAddress("Paris", null)
        assertEquals("Paris", geoAddress.formattedAddress())
    }

    @Test
    fun formattedAddress_AddressWithCountryBlank_ReturnsAddressInCorrectFormat() {
        val geoAddress = GeoAddress("London", "")
        assertEquals("London", geoAddress.formattedAddress())
    }
}