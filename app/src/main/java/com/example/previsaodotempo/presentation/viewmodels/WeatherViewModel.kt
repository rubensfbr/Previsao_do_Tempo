package com.example.previsaodotempo.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual
import com.example.previsaodotempo.domain.usecase.LocationUseCase
import com.example.previsaodotempo.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
) : ViewModel() {

    private val currentWeather = MutableLiveData<PrevisaoAtual>()
    val _currentWeather: LiveData<PrevisaoAtual> = currentWeather

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unidade: String,
        linguagem: String
    ): PrevisaoAtual {
        val weather = weatherUseCase.invoke(lat, lon, unidade, linguagem)
        currentWeather.postValue(weather)
        return weather
    }


}