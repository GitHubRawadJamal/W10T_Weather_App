package com.example.w10t_weather_app.data

import com.google.gson.annotations.SerializedName

data class Main(
    val temp: Float,
    @SerializedName("feels_like") val feelsLike: Float,
    @SerializedName("temp_min") val tempMin: Float,
    @SerializedName("temp_max") val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
)
