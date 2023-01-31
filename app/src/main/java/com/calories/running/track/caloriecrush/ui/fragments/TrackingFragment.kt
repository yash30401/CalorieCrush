package com.calories.running.track.caloriecrush.ui.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentTrackingBinding
import com.calories.running.track.caloriecrush.other.Constants.ACTION_PAUSE_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.MAP_ZOOM
import com.calories.running.track.caloriecrush.other.TrackingUtility
import com.calories.running.track.caloriecrush.services.Polyline
import com.calories.running.track.caloriecrush.services.TrackingService
import com.calories.running.track.caloriecrush.ui.viewmodels.RunningViewmodel
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions


class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private lateinit var binding: FragmentTrackingBinding

    private lateinit var viewmodel: RunningViewmodel

    private var map: GoogleMap? = null
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var currTimeMillis = 0L


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrackingBinding.bind(view)

        binding.mapView.onCreate(savedInstanceState)

        binding.btnStart.setOnClickListener {
            checkPermissionGranted()
            if(TrackingUtility.isLocationEnabled(requireContext())==true) {
                toggleRun()
            }
        }

        binding.mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        subscribeToObserver()
        viewmodel = ViewModelProvider(this).get(RunningViewmodel::class.java)

    }

    private fun checkPermissionGranted() {
        if(ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            enableLocation()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),111)
        }
    }

    private fun enableLocation() {
        val request = com.google.android.gms.location.LocationRequest.create()
        request.priority= com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        request.setInterval(5000)
        request.setFastestInterval(2000)

        val builder= LocationSettingsRequest.Builder()
            .addLocationRequest(request)
        builder.setAlwaysShow(true)

        val result= LocationServices.getSettingsClient(requireContext())
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener {task->
            try{
                val response = task.getResult(ApiException::class.java)
            }catch (e:ApiException){
                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->{
                        try{
                            val resolvableApiException= ResolvableApiException(e.status)
                            resolvableApiException.startResolutionForResult(requireActivity(),88)
                        }catch (e:IntentSender.SendIntentException){

                        }

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE->{

                    }
                }
               e.printStackTrace()
            }
        }
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

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currTimeMillis = it
            val formattedTime = TrackingUtility.getFormattedStopwatchTime(currTimeMillis,true)
            binding.timeTextView.text=formattedTime
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
            binding.btnStart.text = "Start"
            binding.btnFinish.visibility = View.VISIBLE

        } else {
            binding.btnStart.text = "Stop"
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
                .width(10f)
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
                .width(10f)
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