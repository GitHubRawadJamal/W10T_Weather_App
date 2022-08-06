package com.example.w10t_weather_app.data

import com.google.gson.annotations.SerializedName

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)
