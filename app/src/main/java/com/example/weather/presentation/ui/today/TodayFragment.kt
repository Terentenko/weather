package com.example.weather.presentation.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.databinding.FragmentTodayBinding

class TodayFragment : Fragment(),MenuProvider {

    private var _binding: FragmentTodayBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val todayViewModel =
            ViewModelProvider(this)[TodayViewModel::class.java]

        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        todayViewModel.currentWeather.observe(viewLifecycleOwner) {
            textView.text = it.toString()
            binding.toolbar.title=it.city.name
        }
        val toolbar=binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
       menuInflater.inflate(R.menu.toolbar_serch_menu, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId){
            R.menu.toolbar_serch_menu ->{
                true
            }
            else->true
        }
    }
}