package com.calories.running.track.caloriecrush.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentRunBinding
import com.calories.running.track.caloriecrush.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import com.permissionx.guolindev.PermissionX


class RunFragment : Fragment(R.layout.fragment_run) {

    private lateinit var binding: FragmentRunBinding
    private lateinit var fragment: FragmentManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRunBinding.bind(view)

        requestLocationPermissions()

        fragment = requireActivity().supportFragmentManager

        binding.addRunBtnFloating.setOnClickListener {
            fragment.beginTransaction().add(R.id.mainactivity,TrackingFragment()).addToBackStack("run").commit()
        }

    }


    private fun requestLocationPermissions() {
        PermissionX.init(this)
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "Core fundamental are based on these permissions", "OK", "Cancel")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "You need to allow necessary permissions in Settings manually", "OK", "Cancel")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    Toast.makeText(context, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "These permissions are denied: $deniedList", Toast.LENGTH_LONG).show()
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