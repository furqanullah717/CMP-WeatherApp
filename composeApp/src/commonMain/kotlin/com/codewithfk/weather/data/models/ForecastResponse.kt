package com.codewithfk.weather.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastData>,
    val message: Int
)