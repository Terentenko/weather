package com.example.weather.presentation.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.weather.R
import com.example.weather.domain.model.City
import com.example.weather.presentation.PermissionUtils
import com.example.weather.presentation.SharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMapReadyCallback {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var map: GoogleMap
    private var mapView: View? = null
    override fun onMapReady(googleMap: GoogleMap) {
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        googleMap.setOnMapClickListener { latLng ->
            // Створення об'єкту маркера з поточного місця клацання
            val markerOptions = MarkerOptions().position(latLng)

            // Додавання маркера на карту
            map.addMarker(markerOptions)
            sharedViewModel.setCiti(city = City("", latLng = latLng))
            view?.findNavController()?.navigate(R.id.action_navigation_map_to_navigation_today)
        }
        enableMyLocation(googleMap)
        sharedViewModel.city.observe(viewLifecycleOwner) { currentLocationCity ->

            googleMap.addMarker(
                MarkerOptions().position(currentLocationCity.latLng).title(currentLocationCity.name)
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocationCity.latLng))
        }

    }


    private fun enableMyLocation(googleMap: GoogleMap) {

        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            val locationButton =
                (mapView?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(
                    Integer.parseInt("2")
                )
            val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
            // position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            rlp.setMargins(0, 0, 30, 150);
            return
        }

        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            PermissionUtils.RationaleDialog.newInstance(
                LOCATION_PERMISSION_REQUEST_CODE, false
            ).show(parentFragmentManager, "dialog")
            return
        }


        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onMyLocationButtonClick(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle(R.string.location_services_disabled)
            builder.setMessage(R.string.enable_location_message)
            builder.setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()

        } else {


            Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show()
        }
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(context, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
        sharedViewModel.city

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel.permission.observe(viewLifecycleOwner) {
            enableMyLocation(map)
        }
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        if (mapFragment != null) {
            mapView = mapFragment.view
        }
    }

    companion object {

        internal const val LOCATION_PERMISSION_REQUEST_CODE = 21
    }


}
