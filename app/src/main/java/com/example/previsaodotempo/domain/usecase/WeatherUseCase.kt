package com.example.previsaodotempo.domain.usecase

import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual
import com.example.previsaodotempo.domain.repository.WeatherRepositoty
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val repositoty: WeatherRepositoty) {

    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        unidade: String,
        linguagem: String
    ): PrevisaoAtual = repositoty.getCurrentWeather(lat, lon, unidade, linguagem)

}