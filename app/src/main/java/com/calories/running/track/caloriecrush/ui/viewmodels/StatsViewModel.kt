package com.calories.running.track.caloriecrush.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunningDatabase
import com.calories.running.track.caloriecrush.other.SortType
import com.calories.running.track.caloriecrush.respositories.RunningRepository
import kotlinx.coroutines.launch

class StatsViewModel(application: Application):AndroidViewModel(application) {

     val repository: RunningRepository
     val allRunsSortedByDate: LiveData<List<Run>>
     val allRunsSortedByDateASC:LiveData<List<Run>>
     val getTotalTimeInMillis: LiveData<Long>
     val getTotalCaloriesBurned: LiveData<Int>
     val getTotalDistance: LiveData<Int>
     val getTotalAverageSpeed: LiveData<Float>


    init {
        val dao = RunningDatabase.getDatabse(application).getRunDao()
        repository = RunningRepository(dao)

        allRunsSortedByDate=repository.allRunsSortedByDate
        allRunsSortedByDateASC=repository.allRunsSortedByDateASC
        getTotalTimeInMillis=repository.getTotalTimeInMillis
        getTotalCaloriesBurned=repository.getTotalCaloriesBurned
        getTotalDistance=repository.getTotalDistance
        getTotalAverageSpeed=repository.getTotalAverageSpeed

        }

}