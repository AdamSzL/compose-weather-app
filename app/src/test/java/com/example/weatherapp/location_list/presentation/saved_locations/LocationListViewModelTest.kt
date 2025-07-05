package com.example.weatherapp.location_list.presentation.saved_locations

import com.example.weatherapp.core.data.repository.FakeWeatherRepository
import com.example.weatherapp.core.data.repository.WeatherRepository
import com.example.weatherapp.core.fake.fakeLocations
import com.example.weatherapp.core.fake.fakeUserLocation
import com.example.weatherapp.core.presentation.UiText
import com.example.weatherapp.core.utils.MainDispatcherRule
import com.example.weatherapp.location_list.data.repository.saved_locations.FakeSavedLocationsRepository
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_list.domain.use_cases.FetchLocationWeatherBriefUseCase
import com.example.weatherapp.location_search.domain.models.FetchUserLocationError
import com.example.weatherapp.location_search.domain.models.asUiText
import com.example.weatherapp.location_list.presentation.LocationListScreenEvent
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import com.example.weatherapp.location_search.data.repository.location_permission.FakeLocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.location_permission.LocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.user_location.FakeUserLocationRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LocationListViewModelTest {

    private lateinit var viewModel: LocationListViewModel
    private lateinit var userLocationRepository: UserLocationRepository
    private lateinit var fetchLocationWeatherBriefUseCase: FetchLocationWeatherBriefUseCase
    private lateinit var saveLocationUseCase: SaveLocationUseCase
    private lateinit var savedLocationsRepository: SavedLocationsRepository
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var locationPermissionRepository: LocationPermissionRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        savedLocationsRepository = FakeSavedLocationsRepository()
        userLocationRepository = FakeUserLocationRepository()
        locationPermissionRepository = FakeLocationPermissionRepository()
//        viewModel = LocationListViewModel(userLocationRepository, fetchLocationWeatherBriefUseCase, saveLocationUseCase, savedLocationsRepository, weatherRepository, locationPermissionRepository)
    }

//    @Test
//    fun locationsViewModel_SetSavedLocations_UpdatesSavedLocationsState() = runTest {
//        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
//        assertEquals(fakeLocations, viewModel.locationsState.value.locations)
//    }
//
//    @Test
//    fun locationsViewModel_AddMapLocation_CorrectlyAddsLocationToSavedLocations() = runTest {
//        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.AddMapLocation(fakeUserLocation.location.coordinates))
//        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
//        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
//    }
//
//    @Test
//    fun locationsViewModel_DeleteLocation_CorrectlyRemovesLocationFromSavedLocations() = runTest {
//        assertEquals(fakeLocations.size, viewModel.locationsState.value.locations.size)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.DeleteLocation(fakeLocations.first().id))
//        assertEquals(fakeLocations.size - 1, viewModel.locationsState.value.locations.size)
//        assertFalse(viewModel.locationsState.value.locations.contains(fakeLocations.first()))
//    }
//
//    @Test
//    fun locationsViewModel_ShowMessage_CorrectlySetsMessage() {
//        assertEquals(null, viewModel.locationsState.value.message)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.ShowSnackbar(UiText.DynamicString("Test message")))
//        assertEquals(UiText.DynamicString("Test message"), viewModel.locationsState.value.message)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.ResetMessage)
//        assertEquals(null, viewModel.locationsState.value.message)
//    }
//
//    @Test
//    fun locationsViewModel_FetchUserLocation_CorrectlyAddsUserLocationToSavedLocations() = runTest {
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
//        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locations.size)
//        assertTrue(viewModel.locationsState.value.locations.last().location.coordinates == fakeUserLocation.location.coordinates)
//    }
//
//    @Test
//    fun locationsViewModel_FetchUserLocation_SetsErrorMessageWhenPermissionNotGranted() = runTest {
//        savedLocationsRepository = FakeSavedLocationsRepository(shouldReturnError = true)
//        viewModel = LocationListViewModel(deleteLocationUseCase, reverseGeocodeUseCase, savedLocationsRepository, weatherRepository)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
//        assertEquals(
//            (FetchUserLocationError.LocationPermissionNotGranted.asUiText() as UiText.StringResource).resId,
//            (viewModel.locationsState.value.message as UiText.StringResource).resId
//        )
//    }
//
//    @Test
//    fun locationsViewModel_OtherEvents_DoesNothing() {
//        val stateBefore = viewModel.locationsState.value
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.NavigateToLocationMap)
//        assertEquals(stateBefore, viewModel.locationsState.value)
//    }

}