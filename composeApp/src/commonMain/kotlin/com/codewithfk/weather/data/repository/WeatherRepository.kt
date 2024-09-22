package com.codewithfk.weather.data.repository

import com.codewithfk.weather.data.network.ApiService
import dev.icerock.moko.geo.LatLng

class WeatherRepository {
    private val apiService = ApiService()

    suspend fun fetchWeather(location: LatLng) = apiService.getWeather(location)
}