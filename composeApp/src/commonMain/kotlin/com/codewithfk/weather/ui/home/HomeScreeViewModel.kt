package com.codewithfk.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.weather.data.models.WeatherResponse
import com.codewithfk.weather.data.repository.WeatherRepository
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeScreeViewModel(private val locationTracker: LocationTracker) : ViewModel() {

    val repository = WeatherRepository()
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()
    private val _permissionState = MutableStateFlow(PermissionState.NotDetermined)
    val permissionState = _permissionState.asStateFlow()

    init {
        viewModelScope.launch {
            _permissionState.value =
                locationTracker.permissionsController.getPermissionState(Permission.LOCATION)
        }
    }

    private fun fetchWeather(location: LatLng) {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {
                val result = repository.fetchWeather(location)
                _state.value = HomeScreenState.Success(result)
            } catch (e: Exception) {
                println(e.message)
                _state.value = HomeScreenState.Error("Failed to load weather")
            }
        }
    }

    fun provideLocationPermission() {
        viewModelScope.launch {
            val isGranted =
                locationTracker.permissionsController.isPermissionGranted(Permission.LOCATION)
            if (isGranted) {
                _permissionState.value = PermissionState.Granted
                return@launch
            }
            try {
                locationTracker.permissionsController.providePermission(
                    Permission.LOCATION
                )
                _permissionState.value = PermissionState.Granted
            } catch (e: DeniedAlwaysException) {
                _permissionState.value = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                _permissionState.value = PermissionState.Denied
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun updateLocationData() {
        viewModelScope.launch {
            val latLng = getUserLocation()
            fetchWeather(latLng)
        }
    }

    private suspend fun getUserLocation(): LatLng {
        locationTracker.startTracking()
        val location = locationTracker.getLocationsFlow().first()
        locationTracker.stopTracking()
        return location
    }


}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val data: WeatherResponse) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}