package com.calories.running.track.caloriecrush.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentTrackingBinding
import com.calories.running.track.caloriecrush.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.calories.running.track.caloriecrush.services.TrackingService
import com.calories.running.track.caloriecrush.ui.viewmodels.RunningViewmodel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView


class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private lateinit var binding: FragmentTrackingBinding

    private lateinit var viewmodel: RunningViewmodel

    private var map:GoogleMap?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackingBinding.bind(view)

        binding.mapView.onCreate(savedInstanceState)

        binding.btnStart.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }

        binding.mapView.getMapAsync {
            map=it
        }

        viewmodel = ViewModelProvider(this).get(RunningViewmodel::class.java)

    }

    private fun sendCommandToService(action:String)=
        Intent(requireContext(),TrackingService::class.java).also {
            it.action=action
            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

}