package com.example.weather.presentation.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weather.R
import com.example.weather.databinding.FragmentWeekBinding
import com.example.weather.domain.model.CurrentWeather
import com.example.weather.presentation.SharedViewModel

class WeekFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var adapter: WeatherRecyclerAdapter
    private var _binding: FragmentWeekBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val weekViewModel =
            ViewModelProvider(this)[WeekViewModel::class.java]
        sharedViewModel.city.observe(viewLifecycleOwner) {
            weekViewModel.getWeekWeather(latLng = it.latLng)
        }
        weekViewModel.dataLoading.observe(viewLifecycleOwner){
            if(it) binding.progressBar2.visibility = ProgressBar.VISIBLE
            else binding.progressBar2.visibility = ProgressBar.GONE
        }

        _binding = FragmentWeekBinding.inflate(inflater, container, false)
        val root: View = binding.root
        weekViewModel.listCurrentWeather.observe(viewLifecycleOwner) {
            initRecycler(it)
            swipeRefreshLayout = binding.weekLayout
            swipeRefreshLayout.setOnRefreshListener {
                sharedViewModel.city.value?.let { it1 -> weekViewModel.getWeekWeather(it1.latLng) }
                swipeRefreshLayout.isRefreshing = false

            }
        }

        return root
    }

    private fun initRecycler(item: List<CurrentWeather>) {
        adapter = WeatherRecyclerAdapter(listener = object : WeatherRecyclerAdapter.Listener {
            override fun onChooseItem(currentWeather: CurrentWeather) {
                sharedViewModel.setDate(
                    CurrentWeather.turnUTCInto(currentWeather.date, "dd").toInt()
                )
                view?.findNavController()?.navigate(R.id.action_navigation_week_to_detailFragment)
            }
        })
        adapter.listItem = CurrentWeather.averageWeather(item)
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerWeather.layoutManager = layoutManager
        binding.recyclerWeather.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}