package com.example.weather.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentDetailBinding
import com.example.weather.presentation.SharedViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

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


}