package com.codewithfk.weather.data.models
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String?,
    val id: Int?,
    val sunrise: Int?,
    val sunset: Int?,
    val type: Int?
)