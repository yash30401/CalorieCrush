package com.calories.running.track.caloriecrush.adapters

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class ViewPagerAdapter(val items:ArrayList<Fragment>,activityCompat: AppCompatActivity):FragmentStateAdapter(activityCompat){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun createFragment(position: Int): Fragment {
        return items[position]
    }
}