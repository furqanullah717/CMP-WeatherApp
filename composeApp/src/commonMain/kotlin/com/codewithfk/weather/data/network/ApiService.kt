package com.codewithfk.weather.data.network

import com.codewithfk.weather.data.models.WeatherResponse
import dev.icerock.moko.geo.LatLng
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiService {

    private val baseURL = "https://api.openweathermap.org/data/2.5/weather?"
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                encodeDefaults = true
                isLenient = true
                coerceInputValues = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getWeather(location: LatLng): WeatherResponse {
        return httpClient.get("${baseURL}lat=${location.latitude}&lon=${location.longitude}&appid=bff1a3434be828ea41b92d03560528fd&units=metric").body()
    }


}