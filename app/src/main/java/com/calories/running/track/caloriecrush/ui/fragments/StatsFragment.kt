package com.calories.running.track.caloriecrush.ui.fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentStatsBinding


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var binding:FragmentStatsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)
    }
}