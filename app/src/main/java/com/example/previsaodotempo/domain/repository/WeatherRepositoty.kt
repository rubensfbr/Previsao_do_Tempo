package com.example.previsaodotempo.domain.repository

import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual

interface WeatherRepositoty {

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unidade: String,
        linguagem: String
    ): PrevisaoAtual
}