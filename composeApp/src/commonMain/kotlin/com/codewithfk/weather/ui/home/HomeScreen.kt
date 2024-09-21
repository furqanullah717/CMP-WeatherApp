package com.codewithfk.weather.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmpweather.composeapp.generated.resources.Res
import cmpweather.composeapp.generated.resources.ic_cloud
import cmpweather.composeapp.generated.resources.ic_humidity
import cmpweather.composeapp.generated.resources.ic_notification
import cmpweather.composeapp.generated.resources.ic_wind
import com.codewithfk.weather.data.models.WeatherResponse
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen() {
    val viewModel = remember { HomeScreeViewModel() }
    LaunchedEffect(Unit) {
        viewModel.fetchWeather("London")
    }
    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.value) {
            is HomeScreenState.Loading -> {
                CircularProgressIndicator()
                Text(text = "Loading...")

            }

            is HomeScreenState.Success -> {
                val weather = (state.value as HomeScreenState.Success).data
                HomeScreenContent(weather)
            }

            is HomeScreenState.Error -> {
                val message = (state.value as HomeScreenState.Error).message
                Text(text = message)
            }
        }
    }
}

@Composable
fun HomeScreenContent(weather: WeatherResponse) {
    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF7FD4FF),
                    Color(0xFF4A90E2)
                )
            )
        ).systemBarsPadding()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().align(Alignment.TopStart).padding(16.dp)
        ) {
            Text(text = "City: ${weather.name}", color = Color.White)
            Icon(
                painter = painterResource(Res.drawable.ic_notification),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = painterResource(Res.drawable.ic_cloud),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))

            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth().clip(shape = RoundedCornerShape(16.dp))
                    .background(color = Color.White.copy(alpha = 0.1f)).padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${weather.main?.temp?.toInt()}°",
                    style = MaterialTheme.typography.h2.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    fontSize = 80.sp
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = weather.weather.getOrNull(0)?.description ?: "",
                    style = MaterialTheme.typography.h6.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(16.dp))
                WeatherInfoItem(
                    image = Res.drawable.ic_wind,
                    title = "Wind",
                    value = "${weather.wind?.speed} m/s"
                )
                WeatherInfoItem(
                    image = Res.drawable.ic_humidity,
                    title = "Wind",
                    value = "${weather.main?.humidity}%"
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun WeatherInfoItem(image: DrawableResource, title: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = title, color = Color.White)
        Spacer(modifier = Modifier.size(8.dp))
        Text("|", color = Color.White)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = value, color = Color.White)
    }
}









