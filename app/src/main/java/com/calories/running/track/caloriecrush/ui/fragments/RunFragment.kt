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
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentRunBinding
import com.calories.running.track.caloriecrush.other.Constants.REQUEST_CODE_LOCATION_PERMISSION
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RunFragment : Fragment(R.layout.fragment_run) {

    private lateinit var binding: FragmentRunBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRunBinding.bind(view)

        requestLocationPermissions()

    }





    private var currentPermissionIndex = 0
    private val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    private fun requestLocationPermissions() {
        if (currentPermissionIndex >= permissions.size) {
            // All permissions have been granted
            return
        }
        if (ContextCompat.checkSelfPermission(requireContext(), permissions[currentPermissionIndex]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permissions[currentPermissionIndex])) {
                Toast.makeText(context, "Please grant permissions to Use App", Toast.LENGTH_LONG).show()
            }
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permissions[currentPermissionIndex]), 101)
        } else {
            currentPermissionIndex++
            requestLocationPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    requestLocationPermissions()
                } else {
                    //permission denied
                }
                currentPermissionIndex++

                return
            }
        }
    }




}