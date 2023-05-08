package com.example.weather.presentation.ui.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.databinding.CurrentWeatherBinding
import com.example.weather.domain.model.CurrentWeather
import com.squareup.picasso.Picasso


class WeatherRecyclerAdapter(private val listener: Listener) :
    RecyclerView.Adapter<WeatherRecyclerAdapter.CurrentWeatherViewHolder>() {
    var listItem: List<CurrentWeather> = listOf()
        set(value) {
            field = value.toList()
            // todo change to comparator
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
            textDate.text = CurrentWeather.turnUTCInto(item.date, "dd.MM")
            textDate2.text=CurrentWeather.turnUTCIntoDay(item.date)
            recyclerItem.setOnClickListener { listener.onChooseItem(item) }
            textValueMaxt.text = item.tempMax.toString()
            textValueMinT.text = item.tempMin.toString()
            textValueWinSpeed.text = item.winSpeed.toString()
            textValueSunrise.text = CurrentWeather.turnUTCInto(item.sunrise, "HH:mm")
            textValueSunset.text = CurrentWeather.turnUTCInto(item.sunset, "HH:mm")
            textValueT.text=item.temp.toString()
            textValuePresure.text=item.pressure.toString()
            textValueHumidity.text=item.humidity.toString()
            val movieUrl = "${CurrentWeather.URL_ICON_BEGINNING}${item.clouds}${CurrentWeather.URL_ICON_END}"
            Picasso.get()
                .load(movieUrl)
                .resize(50, 50)
                .placeholder(R.drawable.ic_default_24)
                .into(imageWeather)

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
