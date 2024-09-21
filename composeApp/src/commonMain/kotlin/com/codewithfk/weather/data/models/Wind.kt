package com.codewithfk.weather.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Int?,
    val gust: Double? = null,
    val speed: Double?
)