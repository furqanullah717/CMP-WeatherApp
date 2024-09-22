package com.codewithfk.weather.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    val country: String? = null,
    val id: Int? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val type: Int? = null
)