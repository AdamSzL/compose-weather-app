package com.example.weatherapp.location_list.presentation.saved_locations

import com.example.weatherapp.core.data.repository.FakeWeatherRepository
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.core.utils.MainDispatcherRule
import com.example.weatherapp.location_list.data.repository.FakeSavedLocationsRepository
import com.example.weatherapp.location_list.data.repository.SavedLocationsRepository
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_list.domain.use_cases.DeleteLocationUseCase
import com.example.weatherapp.location_list.domain.use_cases.ReverseGeocodeUseCase
import com.example.weatherapp.location_list.presentation.LocationListScreenEvent
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import com.example.weatherapp.location_list.presentation.fake.fakeLocations
import com.example.weatherapp.location_list.presentation.fake.fakeUserLocation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LocationListViewModelTest {

    private lateinit var viewModel: LocationListViewModel
    private lateinit var deleteLocationUseCase: DeleteLocationUseCase
    private lateinit var reverseGeocodeUseCase: ReverseGeocodeUseCase
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var savedLocationsRepository: SavedLocationsRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        deleteLocationUseCase = DeleteLocationUseCase()
        savedLocationsRepository = FakeSavedLocationsRepository()
        weatherRepository = FakeWeatherRepository()
        reverseGeocodeUseCase = ReverseGeocodeUseCase(savedLocationsRepository)
        viewModel = LocationListViewModel(deleteLocationUseCase, reverseGeocodeUseCase, savedLocationsRepository, weatherRepository)
    }

    @Test
    fun locationsViewModel_SetSavedLocations_UpdatesSavedLocationsState() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        assertEquals(fakeLocations, viewModel.locationsState.value.locations)
    }

    @Test
    fun locationsViewModel_AddMapLocation_CorrectlyAddsLocationToSavedLocations() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.AddMapLocation(fakeUserLocation.location.coordinates))
        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
    }

    @Test
    fun locationsViewModel_DeleteLocation_CorrectlyRemovesLocationFromSavedLocations() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.DeleteLocation(fakeLocations.first().id))
        assertEquals(fakeLocations.size - 1, viewModel.locationsState.value.locations.size)
        assertFalse(viewModel.locationsState.value.locations.contains(fakeLocations.first()))
    }

    @Test
    fun locationsViewModel_ShowMessage_CorrectlySetsMessage() {
        assertEquals(null, viewModel.locationsState.value.message)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.ShowSnackbar(UiText.DynamicString("Test message")))
        assertEquals(UiText.DynamicString("Test message"), viewModel.locationsState.value.message)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.ResetMessage)
        assertEquals(null, viewModel.locationsState.value.message)
    }

    @Test
    fun locationsViewModel_FetchUserLocation_CorrectlyAddsUserLocationToSavedLocations() = runTest {
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
    }

    @Test
    fun locationsViewModel_FetchUserLocation_SetsErrorMessageWhenPermissionNotGranted() = runTest {
        savedLocationsRepository = FakeSavedLocationsRepository(shouldReturnError = true)
        viewModel = LocationListViewModel(deleteLocationUseCase, reverseGeocodeUseCase, savedLocationsRepository, weatherRepository)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
        assertEquals(
            (FetchUserLocationError.LocationPermissionNotGranted.asUiText() as UiText.StringResource).resId,
            (viewModel.locationsState.value.message as UiText.StringResource).resId
        )
    }

    @Test
    fun locationsViewModel_OtherEvents_DoesNothing() {
        val stateBefore = viewModel.locationsState.value
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.NavigateToLocationMap)
        assertEquals(stateBefore, viewModel.locationsState.value)
    }

}