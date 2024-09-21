package com.codewithfk.weather.data.repository

import com.codewithfk.weather.data.network.ApiService

class WeatherRepository {
    private val apiService = ApiService()

    suspend fun fetchWeather(city: String) = apiService.getWeather(city)
}