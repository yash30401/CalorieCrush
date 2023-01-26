package com.calories.running.track.caloriecrush.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.adapters.ViewPagerAdapter
import com.calories.running.track.caloriecrush.databinding.ActivityMainBinding
import com.calories.running.track.caloriecrush.ui.fragments.RunFragment
import com.calories.running.track.caloriecrush.ui.fragments.SettingFragment
import com.calories.running.track.caloriecrush.ui.fragments.StatsFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewPager: ViewPager2 =findViewById(R.id.view_pager)

        val fragments:ArrayList<Fragment> = arrayListOf(RunFragment(),StatsFragment(),SettingFragment())

        val adapter=ViewPagerAdapter(fragments,this)
        viewPager.adapter=adapter

        binding.bottomBar.setupWithViewPager2(viewPager)

    }
}