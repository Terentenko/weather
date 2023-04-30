package com.example.weather.presentation.ui.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.CurrentWeatherBinding
import com.example.weather.domain.model.CurrentWeather


class WeatherRecyclerAdapter(private val listener: Listener) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.CurrentWeatherViewHolder>() {
    var listItem: List<CurrentWeather> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()

        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val binding =
            CurrentWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentWeatherViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        val item = listItem[position]
        with(holder.binding) {
            textDate.text = CurrentWeather.turnUTCInto(item.date, "dd.MM HH:mm")
            recyclerItem.setOnClickListener { listener.onChooseItem(item) }
            textValueMaxT.text = item.tempMax.toString()
            textvValueManT.text = item.tempMin.toString()
            textValueProcent.text = item.grndLevel.toString()


            if (item.clouds < 0) {
                imageWeather.setImageResource(R.drawable.ic_cloud_24)

            } else {
                imageWeather.setImageResource(R.drawable.ic_sunny_24)


            }

        }
    }

    override fun getItemCount(): Int =
        listItem.size

    interface Listener {
        fun onChooseItem(currentWeather: CurrentWeather)
    }

    class CurrentWeatherViewHolder(val binding: CurrentWeatherBinding) :
        RecyclerView.ViewHolder(binding.root)


}
