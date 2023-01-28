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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class RunFragment : Fragment(R.layout.fragment_run) {

    private lateinit var binding: FragmentRunBinding
    private lateinit var fragment: FragmentManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRunBinding.bind(view)

        requestFineLocationPermissions()

        fragment = requireActivity().supportFragmentManager

        binding.addRunBtnFloating.setOnClickListener {
            fragment.beginTransaction().add(R.id.mainactivity,TrackingFragment()).addToBackStack("run").commit()
        }

    }


    private fun requestFineLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED
        ) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(
                    context,
                    "Please grant permissions to record audio",
                    Toast.LENGTH_LONG
                )
                    .show()

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED
        ) {

            //IF PERMISSION GRANTED
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