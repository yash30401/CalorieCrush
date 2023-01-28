package com.calories.running.track.caloriecrush.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentTrackingBinding
import com.calories.running.track.caloriecrush.other.Constants.ACTION_PAUSE_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.MAP_ZOOM
import com.calories.running.track.caloriecrush.services.Polyline
import com.calories.running.track.caloriecrush.services.TrackingService
import com.calories.running.track.caloriecrush.ui.viewmodels.RunningViewmodel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.PolylineOptions


class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private lateinit var binding: FragmentTrackingBinding

    private lateinit var viewmodel: RunningViewmodel

    private var map: GoogleMap? = null
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackingBinding.bind(view)

        binding.mapView.onCreate(savedInstanceState)

        binding.btnStart.setOnClickListener {
            toggleRun()
        }

        binding.mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObserver()
        viewmodel = ViewModelProvider(this).get(RunningViewmodel::class.java)

    }

    private fun subscribeToObserver() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })
        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })
    }

    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            binding.btnStart.visibility = View.GONE
            binding.btnResume.visibility = View.VISIBLE
            binding.btnFinish.visibility = View.VISIBLE

        } else {
            binding.btnStart.visibility = View.VISIBLE
            binding.btnResume.visibility = View.GONE
            binding.btnFinish.visibility = View.GONE
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(resources.getColor(R.color.mainLightPurple))
                .width(8f)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            var preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lasLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(resources.getColor(R.color.mainLightPurple))
                .width(8f)
                .add(preLastLatLng)
                .add(lasLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
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