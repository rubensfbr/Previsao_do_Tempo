package com.example.previsaodotempo.data.repository

import android.content.Context
import android.widget.Toast
import com.example.previsaodotempo.data.dto.currentWeather.Current
import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual
import com.example.previsaodotempo.data.remote.OpenWeatherApi
import com.example.previsaodotempo.domain.repository.WeatherRepositoty
import com.example.previsaodotempo.utils.Constants
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
    private val context: Context
) :
    WeatherRepositoty {

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unidade: String,
        linguagem: String
    ): PrevisaoAtual {
        try {
            val response = openWeatherApi.getWeather(lat, lon, unidade, linguagem)
            if (response.isSuccessful && response.body() != null) {
                val previsao = response.body()
                if (previsao != null) {
                    return previsao
                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

        return PrevisaoAtual(
            Current(
                0,
                0.0,
                0,
                0.0,
                0,
                0,
                0,
                0,
                0.0,
                0.0,
                0,
                emptyList(),
                0,
                0.0
            ), emptyList(), emptyList(), 0.0, 0.0, emptyList(), "", 0
        )
    }
}