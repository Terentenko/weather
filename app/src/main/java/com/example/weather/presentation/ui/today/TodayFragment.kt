package com.example.weather.presentation.ui.today

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.weather.BuildConfig
import com.example.weather.R
import com.example.weather.databinding.FragmentTodayBinding
import com.example.weather.domain.model.City
import com.example.weather.presentation.SharedViewModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


class TodayFragment : Fragment(), MenuProvider {

    private var _binding: FragmentTodayBinding? = null
    private lateinit var placesClient: PlacesClient
    private val todayViewModel: TodayViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root
        sharedViewModel.city.observe(viewLifecycleOwner) {
            todayViewModel.getWeather(latLng = it.latLng)
        }
        val textView: TextView = binding.textHome
        todayViewModel.currentWeather.observe(viewLifecycleOwner) {
            textView.text = it.toString()
            if (it.isNullOrEmpty().not()) {
                binding.toolbar.title = it[0].city.name
            }
        }
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // add Menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        // Init Places API
        val apiKey = BuildConfig.MAPS_API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        // Create Places API client
        placesClient = Places.createClient(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_serch_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.app_bar_search -> {
                // Set the fields to specify which types of place data to
                // return after the user has made a selection.
                val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
                // Start the autocomplete intent.
                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(requireContext())
                resultLauncher.launch(intent)
                true
            }

            else -> {
                Toast.makeText(context, "No menu", Toast.LENGTH_SHORT).show()
                true
            }
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    todayViewModel.getWeather(latLng = place.latLng)
                    sharedViewModel.setCiti(
                        City(
                            name = place.name,
                            latLng = place.latLng
                        )
                    )
                    Log.i("Test", "Place: ${place.name}, ${place.id},${place.latLng}")
                }
            }
        }
}

