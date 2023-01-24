package com.calories.running.track.caloriecrush.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunningDatabase
import com.calories.running.track.caloriecrush.respositories.RunningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningViewmodel(application: Application):AndroidViewModel(application) {

    private val repository:RunningRepository
    val allRuns:LiveData<List<Run>>

    init {
        val dao=RunningDatabase.getDatabse(application).getRunDao()
        repository= RunningRepository(dao)
        allRuns=repository.allRuns

    }

    fun insertRun(run: Run)=viewModelScope.launch(Dispatchers.IO) {
        repository.insertRun(run)
    }

    fun deleteRun(run: Run)=viewModelScope.launch(Dispatchers.IO){
        repository.deleteRun(run)
    }

    fun getAllRunsSortedByDate()=viewModelScope.launch (Dispatchers.IO){
        repository.getAllRunsSortedByDate()
    }

    fun getAllRunsSortedByTimeInMillis()=viewModelScope.launch (Dispatchers.IO){
        repository.getAllRunsSortedByTimeInMillis()
    }

    fun getAllRunsSorteByCaloriesBurned()=viewModelScope.launch (Dispatchers.IO){
        repository.getAllRunsSorteByCaloriesBurned()
    }

    fun getAllRunsSortedByAverageSpeed()=viewModelScope.launch (Dispatchers.IO){
        repository.getAllRunsSortedByAverageSpeed()
    }

    fun getAllRunsSortedByDistance()=viewModelScope.launch (Dispatchers.IO){
        repository.getAllRunsSortedByDistance()
    }



}