package com.calories.running.track.caloriecrush.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentSettingBinding


class SettingFragment : Fragment(R.layout.fragment_setting) {


    private lateinit var binding: FragmentSettingBinding
    private var sharerdPreferences: SharedPreferences? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        setupSettingScreen()

        binding.cvName.setOnClickListener {

            binding.etName.focusable = View.FOCUSABLE
            binding.etName.isFocusableInTouchMode=true
            binding.etName.requestFocus()
           val manager:InputMethodManager= activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(binding.etName,0)
        }

    }

    private fun setupSettingScreen() {
        sharerdPreferences = context?.getSharedPreferences("PREF", Context.MODE_PRIVATE)

        val weightUnit = sharerdPreferences?.getString("weight_unit", "-1")
        var weight = sharerdPreferences?.getFloat("weight", 0f)?.toFloat()
        Log.d("Weight", weightUnit.toString())

        binding.etName.setText(sharerdPreferences?.getString("user_name", "User's Name").toString())
        if (weightUnit == "Kg") {
            binding.weightUnit.text = "Kg"
            binding.etWeight.setText(weight.toString())
        } else {
            binding.weightUnit.text = "Pounds"
            weight = weight?.times(2.20462f)
            binding.etWeight.setText(weight.toString())
        }


    }
}
