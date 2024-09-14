package com.example.previsaodotempo.data.remote

import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") unidadde: String,
        @Query("lang") linguagem: String,
    ): Response<PrevisaoAtual>

}