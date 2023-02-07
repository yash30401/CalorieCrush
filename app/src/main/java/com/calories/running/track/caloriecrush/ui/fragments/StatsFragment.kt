package com.calories.running.track.caloriecrush.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.FragmentStatsBinding
import com.calories.running.track.caloriecrush.other.TrackingUtility
import com.calories.running.track.caloriecrush.ui.viewmodels.RunningViewmodel
import com.calories.running.track.caloriecrush.ui.viewmodels.StatsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.math.round


class StatsFragment : Fragment(R.layout.fragment_stats) {

    private lateinit var binding:FragmentStatsBinding
    private lateinit var viewModel:StatsViewModel
    private lateinit var runViewModel: RunningViewmodel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentStatsBinding.bind(view)

        viewModel=ViewModelProvider(this).get(StatsViewModel::class.java)
        runViewModel=ViewModelProvider(this).get(RunningViewmodel::class.java)

        val animUtil = android.view.animation.AnimationUtils.loadAnimation(context,R.anim.slide_right_and_fade_in)
        val animUtil2= AnimationUtils.loadAnimation(context,R.anim.slide_up_and_fadein)
        binding.imageView2.animation=animUtil
        binding.imageView3.animation=animUtil
        binding.imageView4.animation=animUtil
        binding.imageView5.animation=animUtil

        binding.tvTotalTime.animation=animUtil2
        binding.tvTotalDistance.animation=animUtil2
        binding.tvTotalAverageSpeed.animation=animUtil2
        binding.tvTotalCalBurned.animation=animUtil2

        binding.textView5.animation=animUtil2
        binding.textView6.animation=animUtil2
        binding.textView7.animation=animUtil2
        binding.textView8.animation=animUtil2

        subscribeToObserver()
        setupChart()


    }

    private fun setupChart(){
        runViewModel.allRunsSortedByDateASC.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = it.mapIndexed { index, run ->
                    com.github.mikephil.charting.data.Entry(index.toFloat(), run.avgSpeedInKMH.toFloat())
                }

                val distanceRan = it.mapIndexed{ index, run ->
                    com.github.mikephil.charting.data.Entry(index.toFloat(), run.distanceInMeters/1000f)
                }

                val calBurned =it.mapIndexed{index, run ->
                    com.github.mikephil.charting.data.Entry(index.toFloat(), run.caloriesBurned.toFloat())
                }



                val avgSpeedDataSet = LineDataSet(avgSpeed, "Avg Speed(in Km/h)")
                avgSpeedDataSet.color=resources.getColor(R.color.bottomBarDark)
                avgSpeedDataSet.circleRadius = 5f
                avgSpeedDataSet.setDrawFilled(true)
                avgSpeedDataSet.valueTextSize = 12F
                avgSpeedDataSet.fillColor = resources.getColor(R.color.mainLightPurple)
                avgSpeedDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

                val DistanceRanDataSet = LineDataSet(distanceRan, "Distance(in Km)")
                DistanceRanDataSet.color=resources.getColor(R.color.chartcolorTotalDistanceLine)
                DistanceRanDataSet.circleRadius = 5f
                DistanceRanDataSet.setDrawFilled(true)
                DistanceRanDataSet.valueTextSize = 12F
                DistanceRanDataSet.fillColor = resources.getColor(R.color.chartcolorTotalDistanceFill)
                DistanceRanDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

                val calBurnedDataSet = LineDataSet(calBurned, "Calories Burned(in Kcal)")
                calBurnedDataSet.color=resources.getColor(R.color.chartcolorcaloriesBurnedline)
                calBurnedDataSet.circleRadius = 5f
                calBurnedDataSet.setDrawFilled(true)
                calBurnedDataSet.valueTextSize = 12F
                calBurnedDataSet.fillColor = resources.getColor(R.color.chartcolorcaloriesBurnedfill)
                calBurnedDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);


                val lineData = LineData(avgSpeedDataSet,DistanceRanDataSet,calBurnedDataSet)

                binding.lineChart.data = lineData

                binding.lineChart.animateXY(2000, 2000, Easing.EaseInCubic)
                binding.lineChart.invalidate()


            }
        })
    }


    private fun subscribeToObserver(){
        viewModel.getTotalTimeInMillis.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTimeRun = TrackingUtility.getFormattedStopwatchTime(it)
                    binding.tvTotalTime.text=totalTimeRun

            }
        })

        viewModel.getTotalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it/1000f
                val totalDistance= round(km*10f)/10f
                val totalDistanceString = "${totalDistance}Km"
                binding.tvTotalDistance.text=totalDistanceString
            }
        })

        viewModel.getTotalAverageSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
               val avgSpeed = round(it*10f)/10f
                val avergeSpeedString = "${avgSpeed}km/h"
                binding.tvTotalAverageSpeed.text=avergeSpeedString
            }
        })

        viewModel.getTotalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCal = "${it}Kcal"
                binding.tvTotalCalBurned.text=totalCal
            }
        })
    }

}