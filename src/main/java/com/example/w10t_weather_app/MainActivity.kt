package com.example.w10t_weather_app

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.w10t_weather_app.data.OpenWeatherResponse
import com.example.w10t_weather_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlin.math.floor
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val client = OkHttpClient()
    private val apiUrl: String = "https://api.openweathermap.org/data/2.5/weather"
    private val apiKey: String = "70831f87638412ee265c34158a8cdd04"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.btnMakeRequest?.setOnClickListener{
            makeRequestUsingOKHTTP(binding?.inputParameter?.text.toString())
        }

    }

    private fun makeRequestUsingOKHTTP(cityName: String) {


        var url = "${apiUrl}?q=${cityName}&appid=${apiKey}"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i(TAG, "fail: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString, OpenWeatherResponse::class.java)
                    runOnUiThread {
                        if(result.city!=null) {
                            binding?.tvCity?.text = result.city.toString()
                            binding?.tvCountry?.text = result.sys.country.toString()
                            binding?.tvDescription?.text =  result.weather.joinToString { it.description }.capitalize()
                            binding?.tvTemp?.text =  ((result.main.temp-273.15).toInt()).toString()
                            binding?.tvFeelsLike?.text = ((result.main.feelsLike-273.15).toInt()).toString()
                            binding?.tvTempMin?.text =  ((result.main.tempMin-273.15).toInt()).toString()
                            binding?.tvTempMax?.text =  ((result.main.tempMax-273.15).toInt()).toString()
                            binding?.tvPressure?.text =  result.main.pressure.toString()
                            binding?.tvHumidity?.text =  result.main.humidity.toString() + "%"
                            binding?.tvVisibility?.text =  result.visibility.toString()
                            binding?.tvWindSpeed?.text =  (floor((result.wind.speed/1000)/3600)).toString() + "km/hr"
                            binding?.tvWindDeg?.text =  result.wind.deg.toString()
                            binding?.tvCloudsAll?.text =  result.clouds.all.toString() + "%"

                            binding?.constraintWelcomeCard?.visibility = View.GONE
                            binding?.itvInputError?.visibility = View.GONE
                            binding?.constraintLocation?.visibility = View.VISIBLE
                            binding?.constraintWeather?.visibility = View.VISIBLE
                        }
                        else{
                            binding?.constraintWelcomeCard?.visibility = View.GONE
                            binding?.constraintLocation?.visibility = View.GONE
                            binding?.constraintWeather?.visibility = View.GONE
                            binding?.itvInputError?.visibility = View.VISIBLE
                        }
                    }
                }

            }
        })
    }
    companion object{
        const val TAG = "WEATHER_TAG"
    }
}