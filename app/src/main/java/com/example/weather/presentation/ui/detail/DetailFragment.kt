package com.example.weather.presentation.ui.detail

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailBinding
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.presentation.SharedViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class DetailFragment : Fragment() {
    private val detailViewModel: DetailViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedViewModel.date.observe(viewLifecycleOwner) {
        sharedViewModel.city.value?.let { it1 -> detailViewModel.getWeatherDate(latLng = it1.latLng,date= it) }
            detailViewModel.listCurrentWeather.observe(viewLifecycleOwner){
                if(it.isNullOrEmpty().not()){
                binding.toolbarDetail.title=it[0].city.name
                    initChart(it)
                    initCurrentWeatherToday(currentWeather = CurrentWeather.listCurrentWeatherToAverageWeather(it))

                }
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarDetail.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbarDetail.setNavigationOnClickListener {

            it.findNavController().popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initChart(currentWeather: List<CurrentWeather>) {
        val chart = binding.diagramDetail.chart
        val dataSetMaxT = LineDataSet(currentWeather.map {
            Entry(
                CurrentWeather.turnUTCInto(it.date, "HH").toFloat(),
                it.tempMax.toFloat()
            )
        }, "max t")
        dataSetMaxT.color = Color.GREEN
        dataSetMaxT.valueTextColor = Color.GREEN
        val dataSetMinT = LineDataSet(currentWeather.map {
            Entry(
                CurrentWeather.turnUTCInto(it.date, "HH").toFloat(),
                it.tempMin.toFloat()
            )
        }, "min t")
        dataSetMinT.color = Color.BLUE
        dataSetMinT.valueTextColor = Color.BLUE

        val lineData = LineData(dataSetMaxT, dataSetMinT)
        chart.data = lineData

        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)

        // chart.axisLeft.textColor = Color.BLACK
        // chart.xAxis.textColor = Color.BLACK
        chart.legend.textColor = Color.BLACK
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        // chart.description.text = "Sine Wave"
        chart.description.textColor = Color.BLACK
        chart.invalidate()
    }

    private fun initCurrentWeatherToday(currentWeather: CurrentWeather) {

        with(binding.currentWeatherDetail) {
            textDate.text = CurrentWeather.turnUTCInto(
                currentWeather.date,
                "dd.MM"
            )
            textDate2.text = CurrentWeather.turnUTCIntoDay(currentWeather.date)
            textValueMaxt.text = currentWeather.tempMax.toString()
            textValueMinT.text = currentWeather.tempMin.toString()
            textValueWinSpeed.text = currentWeather.winSpeed.toString()
            textValueSunrise.text = CurrentWeather.turnUTCInto(
                currentWeather.sunrise,
                "HH:mm"
            )
            textValueSunset.text = CurrentWeather.turnUTCInto(
                currentWeather.sunset,
                "HH:mm"
            )
            textValueT.text = currentWeather.temp.toString()
            textValuePresure.text = currentWeather.pressure.toString()
            textValueHumidity.text = currentWeather.humidity.toString()


            if (currentWeather.clouds < 0) {
                imageWeather.setImageResource(R.drawable.ic_cloud_24)

            } else {
                imageWeather.setImageResource(R.drawable.ic_sunny_24)


            }

        }
    }


}