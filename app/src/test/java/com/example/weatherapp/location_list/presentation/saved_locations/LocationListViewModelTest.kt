package com.example.weatherapp.location_list.presentation.saved_locations

import com.example.weatherapp.core.fake.fakeLocations
import com.example.weatherapp.core.fake.fakeUserLocation
import com.example.weatherapp.core.utils.MainDispatcherRule
import com.example.weatherapp.location_list.data.repository.saved_locations.FakeSavedLocationsRepository
import com.example.weatherapp.location_list.data.repository.saved_locations.SavedLocationsRepository
import com.example.weatherapp.location_list.di.RefreshSavedLocationsWeatherBriefUseCase
import com.example.weatherapp.location_list.domain.use_cases.FetchLocationWeatherBriefUseCase
import com.example.weatherapp.location_list.presentation.LocationListScreenEvent
import com.example.weatherapp.location_list.presentation.LocationListViewModel
import com.example.weatherapp.location_search.data.repository.location_permission.FakeLocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.location_permission.LocationPermissionRepository
import com.example.weatherapp.location_search.data.repository.user_location.FakeUserLocationRepository
import com.example.weatherapp.location_search.data.repository.user_location.UserLocationRepository
import com.example.weatherapp.location_search.domain.use_cases.SaveLocationUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LocationListViewModelTest {

    private lateinit var viewModel: LocationListViewModel
    private lateinit var userLocationRepository: UserLocationRepository
    private lateinit var fetchLocationWeatherBriefUseCase: FetchLocationWeatherBriefUseCase
    private lateinit var refreshSavedLocationsWeatherBriefUseCase: RefreshSavedLocationsWeatherBriefUseCase
    private lateinit var saveLocationUseCase: SaveLocationUseCase
    private lateinit var savedLocationsRepository: SavedLocationsRepository
    private lateinit var locationPermissionRepository: LocationPermissionRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        savedLocationsRepository = FakeSavedLocationsRepository()
        userLocationRepository = FakeUserLocationRepository()
        locationPermissionRepository = FakeLocationPermissionRepository()
        viewModel = LocationListViewModel(userLocationRepository, fetchLocationWeatherBriefUseCase, saveLocationUseCase, refreshSavedLocationsWeatherBriefUseCase, savedLocationsRepository, locationPermissionRepository)
    }

    @Test
    fun locationsViewModel_SetSavedLocations_UpdatesSavedLocationsState() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locationsWithWeatherBrief.size)
        assertEquals(fakeLocations, viewModel.locationsState.value.locationsWithWeatherBrief.map { it.location })
    }

    @Test
    fun locationsViewModel_DeleteLocation_CorrectlyRemovesLocationFromSavedLocations() = runTest {
        assertEquals(fakeLocations.size, viewModel.locationsState.value.locationsWithWeatherBrief.size)
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.DeleteLocation(fakeLocations.first().id))
        assertEquals(fakeLocations.size - 1, viewModel.locationsState.value.locationsWithWeatherBrief.size)
        assertFalse(viewModel.locationsState.value.locationsWithWeatherBrief.map { it.location }.contains(fakeLocations.first()))
    }

    @Test
    fun locationsViewModel_FetchUserLocation_CorrectlyAddsUserLocationToSavedLocations() = runTest {
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
        assertEquals(fakeLocations.size + 1, viewModel.locationsState.value.locationsWithWeatherBrief.size)
        assertTrue(viewModel.locationsState.value.locationsWithWeatherBrief.last().location.coordinates == fakeUserLocation.coordinates)
    }

//    @Test
//    fun locationsViewModel_FetchUserLocation_SetsErrorMessageWhenPermissionNotGranted() = runTest {
//        savedLocationsRepository = FakeSavedLocationsRepository(shouldReturnError = true)
//        viewModel = LocationListViewModel(userLocationRepository, fetchLocationWeatherBriefUseCase, saveLocationUseCase, refreshSavedLocationsWeatherBriefUseCase, savedLocationsRepository, locationPermissionRepository)
//        viewModel.onLocationsScreenEvent(LocationListScreenEvent.FetchUserLocation)
//        assertEquals(
//            (FetchUserLocationError.LocationPermissionNotGranted.asUiText() as UiText.StringResource).resId,
//            (viewModel.locationsState.value.message as UiText.StringResource).resId
//        )
//    }

    @Test
    fun locationsViewModel_OtherEvents_DoesNothing() {
        val stateBefore = viewModel.locationsState.value
        viewModel.onLocationsScreenEvent(LocationListScreenEvent.NavigateToLocationMap)
        assertEquals(stateBefore, viewModel.locationsState.value)
    }

}