package com.codewithfk.weather.data.models
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: Main?,
    val name: String?,
    val visibility: Int?,
    val weather: List<Weather>,
    val wind: Wind?
)