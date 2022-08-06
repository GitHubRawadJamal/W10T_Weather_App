package com.example.w10t_weather_app.data

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val visibility: Int,
    val clouds: Clouds,
    val sys: Sys,
    @SerializedName("name") val city: String
)
