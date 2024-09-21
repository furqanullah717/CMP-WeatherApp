package com.codewithfk.weather.ui.home

import com.codewithfk.weather.data.models.WeatherResponse
import com.codewithfk.weather.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreeViewModel {

    val repository = WeatherRepository()
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    fun fetchWeather(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = HomeScreenState.Loading
            try {
                val result = repository.fetchWeather(city)
                _state.value = HomeScreenState.Success(result)
            } catch (e: Exception) {
                println(e.message)
                _state.value = HomeScreenState.Error("Failed to load weather")
            }
        }
    }


}

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Success(val data: WeatherResponse) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}