package com.calories.running.track.caloriecrush.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunningDatabase
import com.calories.running.track.caloriecrush.other.SortType
import com.calories.running.track.caloriecrush.respositories.RunningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningViewmodel(application: Application):AndroidViewModel(application) {

    private val repository:RunningRepository
    private val allRuns:LiveData<List<Run>>
    private val allRunsSortedByDate:LiveData<List<Run>>
    private val allRunsSortedByDistance:LiveData<List<Run>>
    private val allRunsSortedByCalBurned:LiveData<List<Run>>
    private val allRunsSortedByTimeInMillis:LiveData<List<Run>>
    private val allRunsSortedByAvgSpeed:LiveData<List<Run>>

    val runs=MediatorLiveData<List<Run>>()

    val sortType=SortType.DATE
    init {
        val dao=RunningDatabase.getDatabse(application).getRunDao()
        repository= RunningRepository(dao)
        allRuns=repository.allRuns
        allRunsSortedByDate=repository.allRunsSortedByDate
        allRunsSortedByDistance=repository.allRunsSortedByDistance
        allRunsSortedByCalBurned=repository.allRunsSortedByCalBurned
        allRunsSortedByTimeInMillis=repository.allRunsSortedByTimeInMillis
        allRunsSortedByAvgSpeed=repository.allRunsSortedByAvgSpeed

        runs.addSource(allRunsSortedByDate){result->
            if(sortType == SortType.DATE){
                result?.let {
                    runs.value=it
                }
            }
        }
    }

    fun insertRun(run: Run)=viewModelScope.launch(Dispatchers.IO) {
        repository.insertRun(run)
    }

    fun deleteRun(run: Run)=viewModelScope.launch(Dispatchers.IO){
        repository.deleteRun(run)
    }




}