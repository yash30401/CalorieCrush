package com.calories.running.track.caloriecrush.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Adapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.adapters.RunAdapter
import com.calories.running.track.caloriecrush.databinding.FragmentRunBinding
import com.calories.running.track.caloriecrush.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.calories.running.track.caloriecrush.other.SortType
import com.calories.running.track.caloriecrush.ui.viewmodels.RunningViewmodel
import com.permissionx.guolindev.PermissionX


class RunFragment : Fragment(R.layout.fragment_run) {

    private lateinit var binding: FragmentRunBinding
    private lateinit var fragment: FragmentManager
    private lateinit var runAdapter: RunAdapter
    private lateinit var viewModel:RunningViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRunBinding.bind(view)

        binding.addRunBtnFloating.animation=AnimationUtils.loadAnimation(context,R.anim.slide_up)
        requestLocationPermissions()
        viewModel=ViewModelProvider(this).get(RunningViewmodel::class.java)
        setupRecyclerView()

//        when(viewModel.sortType){
//            SortType.DATE->spF
//        }

        viewModel.runs.observe(viewLifecycleOwner, Observer {
            runAdapter.submitList(it)
        })

        fragment = requireActivity().supportFragmentManager
        binding.addRunBtnFloating.setOnClickListener {
            fragment.beginTransaction().add(R.id.mainactivity, TrackingFragment())
                .addToBackStack("run").commit()
        }

    }

    private fun setupRecyclerView() = binding.rvRuns.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
        layoutManager = LinearLayoutManager(requireContext())

    }


    private fun requestLocationPermissions() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Log.d("Granted", "All permissions are granted")
                } else {
                    Toast.makeText(
                        context,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    //Handling Permission callback
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.size > 0
                    && grantResults[0] == PERMISSION_GRANTED
                ) {
                    // permission was granted, yay!

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, "Permissions Denied to record audio", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
        }
    }


}