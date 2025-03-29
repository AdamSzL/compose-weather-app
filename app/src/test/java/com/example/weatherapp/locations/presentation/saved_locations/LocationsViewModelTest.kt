package com.example.weatherapp.locations.presentation.saved_locations

import com.example.weatherapp.core.data.repository.FakeWeatherRepository
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.core.utils.MainDispatcherRule
import com.example.weatherapp.locations.data.repository.FakeLocationRepository
import com.example.weatherapp.locations.data.repository.LocationRepository
import com.example.weatherapp.locations.domain.models.FetchUserLocationError
import com.example.weatherapp.locations.domain.models.asUiText
import com.example.weatherapp.locations.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.locations.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeLocations
import com.example.weatherapp.locations.presentation.saved_locations.fake.fakeUserLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LocationsViewModelTest {

    private lateinit var viewModel: LocationsViewModel
    private lateinit var deleteLocationUseCase: DeleteLocationUseCase
    private lateinit var reverseGeocodeUseCase: ReverseGeocodeUseCase
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationRepository: LocationRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        deleteLocationUseCase = DeleteLocationUseCase()
        locationRepository = FakeLocationRepository()
        weatherRepository = FakeWeatherRepository()
        reverseGeocodeUseCase = ReverseGeocodeUseCase(locationRepository)
        viewModel = LocationsViewModel(deleteLocationUseCase, reverseGeocodeUseCase, locationRepository, weatherRepository)
    }

    @Test
    fun locationsViewModel_SetSavedLocations_UpdatesSavedLocationsState() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        assertEquals(fakeLocations, viewModel.locationsState.value.locations)
    }

    @Test
    fun locationsViewModel_AddMapLocation_CorrectlyAddsLocationToSavedLocations() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.AddMapLocation(fakeUserLocation.location.coordinates))
        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
    }

    @Test
    fun locationsViewModel_DeleteLocation_CorrectlyRemovesLocationFromSavedLocations() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.DeleteLocation(fakeLocations.first().id))
        assertEquals(fakeLocations.size - 1, viewModel.locationsState.value.locations.size)
        assertFalse(viewModel.locationsState.value.locations.contains(fakeLocations.first()))
    }

    @Test
    fun locationsViewModel_ShowMessage_CorrectlySetsMessage() {
        assertEquals(null, viewModel.locationsState.value.message)
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.ShowSnackbar(UiText.DynamicString("Test message")))
        assertEquals(UiText.DynamicString("Test message"), viewModel.locationsState.value.message)
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.ResetMessage)
        assertEquals(null, viewModel.locationsState.value.message)
    }

    @Test
    fun locationsViewModel_FetchUserLocation_CorrectlyAddsUserLocationToSavedLocations() = runTest {
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.FetchUserLocation)
        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
    }

    @Test
    fun locationsViewModel_FetchUserLocation_SetsErrorMessageWhenPermissionNotGranted() = runTest {
        locationRepository = FakeLocationRepository(shouldReturnError = true)
        viewModel = LocationsViewModel(deleteLocationUseCase, reverseGeocodeUseCase, locationRepository, weatherRepository)
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.FetchUserLocation)
        assertEquals(
            (FetchUserLocationError.LocationPermissionNotGranted.asUiText() as UiText.StringResource).resId,
            (viewModel.locationsState.value.message as UiText.StringResource).resId
        )
    }

    @Test
    fun locationsViewModel_OtherEvents_DoesNothing() {
        val stateBefore = viewModel.locationsState.value
        viewModel.onLocationsScreenEvent(LocationsScreenEvent.NavigateToLocationMap)
        assertEquals(stateBefore, viewModel.locationsState.value)
    }

}