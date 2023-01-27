package com.calories.running.track.caloriecrush.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.adapters.ViewPagerAdapter
import com.calories.running.track.caloriecrush.databinding.ActivityMainBinding
import com.calories.running.track.caloriecrush.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.calories.running.track.caloriecrush.ui.fragments.RunFragment
import com.calories.running.track.caloriecrush.ui.fragments.SettingFragment
import com.calories.running.track.caloriecrush.ui.fragments.StatsFragment
import com.calories.running.track.caloriecrush.ui.fragments.TrackingFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragment = supportFragmentManager

        navigateToTrackingFragmentIfNeeded(intent)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        val fragments: ArrayList<Fragment> =
            arrayListOf(RunFragment(), StatsFragment(), SettingFragment())

        val adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter

        binding.bottomBar.setupWithViewPager2(viewPager)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentIfNeeded(intent)
    }

    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            fragment.beginTransaction().add(R.id.mainactivity, TrackingFragment())
                .addToBackStack("run").commit()
        }
    }

}