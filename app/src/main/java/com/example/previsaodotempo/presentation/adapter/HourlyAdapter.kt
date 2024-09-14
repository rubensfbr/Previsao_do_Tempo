package com.example.previsaodotempo.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.previsaodotempo.data.dto.currentWeather.Hourly
import com.example.previsaodotempo.databinding.ItemDailyBinding
import com.example.previsaodotempo.utils.Constants
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import kotlin.math.roundToInt

class HourlyAdapter : ListAdapter<Hourly, HourlyViewHolder>(diffCallback) {

    fun setList(list: List<Hourly>) {
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(
            ItemDailyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val diffCallback = object : ItemCallback<Hourly>() {
            override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class HourlyViewHolder(private val binding: ItemDailyBinding) : ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: Hourly) {
        binding.apply {
            textHora.text =
                "${
                    Instant.ofEpochSecond(item.dt.toLong())
                        .atZone(ZoneId.systemDefault()).hour
                }:00"
            textTemp.text = "${item.temp.roundToInt()}º"
            Picasso.get()
                .load("${Constants.ICON_URL}${item.weather[0].icon}.png")
                .into(imageTemp)
        }
    }

}