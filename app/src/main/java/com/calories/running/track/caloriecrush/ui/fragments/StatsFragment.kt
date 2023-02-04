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


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var binding:FragmentStatsBinding
    private lateinit var viewModel:StatsViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)

        viewModel=ViewModelProvider(this).get(StatsViewModel::class.java)


    }

    private fun subscribeToObserver(){
        viewModel.getTotalTimeInMillis.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopwatchTime(it)

            }
        })
    }

}