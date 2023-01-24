package com.calories.running.track.caloriecrush.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentSettingBinding


class SettingFragment : Fragment(R.layout.fragment_setting) {


    private lateinit var binding:FragmentSettingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSettingBinding.bind(view)
    }
}
