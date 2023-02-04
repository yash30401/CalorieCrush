package com.calories.running.track.caloriecrush.ui.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentStatsBinding
import com.calories.running.track.caloriecrush.other.TrackingUtility
import com.calories.running.track.caloriecrush.ui.viewmodels.StatsViewModel
import kotlin.math.round


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var binding:FragmentStatsBinding
    private lateinit var viewModel:StatsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)

        viewModel=ViewModelProvider(this).get(StatsViewModel::class.java)
        subscribeToObserver()

    }

    private fun subscribeToObserver(){
        viewModel.getTotalTimeInMillis.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopwatchTime(it)
                    binding.tvTotalTime.text=totalTimeRun
            }
        })

        viewModel.getTotalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it/1000f
                val totalDistance= round(km*10f)/10f
                val totalDistanceString = "${totalDistance}Km"
                binding.tvTotalDistance.text=totalDistanceString
            }
        })

        viewModel.getTotalAverageSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
               val avgSpeed = round(it*10f)/10f
                val avergeSpeedString = "${avgSpeed}km/h"
                binding.tvTotalAverageSpeed.text=avergeSpeedString
            }
        })

        viewModel.getTotalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCal = "${it}Kcal"
                binding.tvTotalCalBurned.text=totalCal
            }
        })
    }

}