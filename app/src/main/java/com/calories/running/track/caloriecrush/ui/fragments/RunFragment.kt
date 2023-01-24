package com.calories.running.track.caloriecrush.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentRunBinding

class RunFragment : Fragment(R.layout.fragment_run) {

    private lateinit var binding:FragmentRunBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentRunBinding.bind(view)


    }
}