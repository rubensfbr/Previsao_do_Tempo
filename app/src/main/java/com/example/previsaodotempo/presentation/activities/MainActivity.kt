package com.example.previsaodotempo.presentation.activities

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.previsaodotempo.data.dto.currentWeather.PrevisaoAtual
import com.example.previsaodotempo.databinding.ActivityMainBinding
import com.example.previsaodotempo.presentation.adapter.HourlyAdapter
import com.example.previsaodotempo.presentation.helper.Permissoes
import com.example.previsaodotempo.presentation.viewmodels.WeatherViewModel
import com.example.previsaodotempo.utils.Constants
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<WeatherViewModel>()

    private lateinit var recyclerView: RecyclerView
    private val adapter = HourlyAdapter()

    private var latitude = 0.0
    private var longitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Permissoes().verificarPermissoes(this)

        callRecyclerView()

    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

        carregando(true)

        LocationServices.getFusedLocationProviderClient(this).lastLocation
            .addOnCompleteListener { location ->
                longitude = location.result.longitude
                latitude = location.result.latitude
                lifecycleScope.launch {
                    viewModel.getCurrentWeather(latitude, longitude, "metric", "pt_br")
                    carregando(false)
                }
                val geocoder = Geocoder(this, Locale.getDefault())
                val local = geocoder.getFromLocation(latitude, longitude, 1)!!

                binding.textLocalidade.text = local[0].subAdminArea .toString()
            }


    }

    override fun onResume() {
        super.onResume()

        observer()

    }

    private fun callRecyclerView() {
        recyclerView = binding.hourlyRecycler
//        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {

        viewModel._currentWeather.observe(this) {
            binding.apply {
                textCurrentTemp.text = "${it.current.temp.roundToInt()}°"

                if (it.daily.isNotEmpty()) {

//                    Resumo dia
                    textResume.text = it.current.weather[0].description

//                    Previsão para 10 Dias
                    previsao10DiasDias(it)
                    previsao10DiasIcons(it)
                    previsao10DiasTemp(it)

//                    Nascer do sol
                    val sunrise = it.current.sunrise.toLong()
                    val hour = Instant.ofEpochSecond(sunrise).atZone(ZoneId.systemDefault()).hour
                    val minute = Instant.ofEpochSecond(sunrise).atZone(ZoneId.systemDefault()).minute
                    textSunrise.text = "${hour}:$minute"

//                    Por do Sol
                    val sunset = it.current.sunset.toLong()
                    val hour1 = Instant.ofEpochSecond(sunset).atZone(ZoneId.systemDefault()).hour
                    val minute1 = Instant.ofEpochSecond(sunset).atZone(ZoneId.systemDefault()).minute
                    textSunset.text = "${hour1}:$minute1"

//                    Vento
                    textWind.text = "${(it.hourly[0].wind_speed * 3.6).roundToInt()} Km/h"

//                    Umidade
                    textUmidade.text = "${it.hourly[0].humidity}%"

//                    Sensação termica
                    textFeelsLike.text = "${it.hourly[0].feels_like.roundToInt()}º"

//                    UV
                    textUV.text = it.hourly[0].uvi.toString()

//                    Possibilidade de chuva
                    textPossibilidadeChuva.text = "${it.daily[0].pop * 100}%"

//                    previsao horaria

                    adapter.setList(it.hourly)

                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun previsao10DiasDias(it: PrevisaoAtual) {
        binding.apply {
            textDay1.text = retornoData(it.daily[0].dt.toLong()).toString()
            textDay2.text = retornoData(it.daily[1].dt.toLong()).toString()
            textDay3.text = retornoData(it.daily[2].dt.toLong()).toString()
            textDay4.text = retornoData(it.daily[3].dt.toLong()).toString()
            textDay5.text = retornoData(it.daily[4].dt.toLong()).toString()
            textDay6.text = retornoData(it.daily[5].dt.toLong()).toString()
            textDay7.text = retornoData(it.daily[6].dt.toLong()).toString()
            textDay8.text = retornoData(it.daily[7].dt.toLong()).toString()
        }
    }

//    private fun retornoData(data: Long): DayOfWeek {
    private fun retornoData(data: Long): String {
        return Instant.ofEpochSecond(data).atZone(ZoneId.systemDefault())
            .dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).uppercase()
    }

    private fun previsao10DiasIcons(it: PrevisaoAtual) {
        binding.apply {
            Picasso.get().load("${Constants.ICON_URL}${it.daily[0].weather[0].icon}.png")
                .into(imageDay1)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[1].weather[0].icon}.png")
                .into(imageDay2)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[2].weather[0].icon}.png")
                .into(imageDay3)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[3].weather[0].icon}.png")
                .into(imageDay4)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[4].weather[0].icon}.png")
                .into(imageDay5)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[5].weather[0].icon}.png")
                .into(imageDay6)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[6].weather[0].icon}.png")
                .into(imageDay7)
            Picasso.get().load("${Constants.ICON_URL}${it.daily[7].weather[0].icon}.png")
                .into(imageDay8)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun previsao10DiasTemp(it: PrevisaoAtual) {
        binding.apply {
            textTempDay1.text =
                "${it.daily[0].temp.max.roundToInt()}° / ${it.daily[0].temp.min.roundToInt()}°"
            textTempDay2.text =
                "${it.daily[1].temp.max.roundToInt()}° / ${it.daily[1].temp.min.roundToInt()}°"
            textTempDay3.text =
                "${it.daily[2].temp.max.roundToInt()}° / ${it.daily[2].temp.min.roundToInt()}°"
            textTempDay4.text =
                "${it.daily[3].temp.max.roundToInt()}° / ${it.daily[3].temp.min.roundToInt()}°"
            textTempDay5.text =
                "${it.daily[4].temp.max.roundToInt()}° / ${it.daily[4].temp.min.roundToInt()}°"
            textTempDay6.text =
                "${it.daily[5].temp.max.roundToInt()}° / ${it.daily[5].temp.min.roundToInt()}°"
            textTempDay7.text =
                "${it.daily[6].temp.max.roundToInt()}° / ${it.daily[6].temp.min.roundToInt()}°"
            textTempDay8.text =
                "${it.daily[7].temp.max.roundToInt()}° / ${it.daily[7].temp.min.roundToInt()}°"
        }
    }

    private fun carregando(exibir: Boolean) {
        if (exibir) {
            binding.progressBar.visibility = View.VISIBLE
            binding.groupMain.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.groupMain.visibility = View.VISIBLE
        }
    }

}