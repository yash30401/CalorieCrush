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

class RunningViewmodel(application: Application) : AndroidViewModel(application) {

    private val repository: RunningRepository
     val allRuns: LiveData<List<Run>>
     val allRunsSortedByDate: LiveData<List<Run>>
    val allRunsSortedByDateASC:LiveData<List<Run>>
     val allRunsSortedByDistance: LiveData<List<Run>>
     val allRunsSortedByCalBurned: LiveData<List<Run>>
     val allRunsSortedByTimeInMillis: LiveData<List<Run>>
     val allRunsSortedByAvgSpeed: LiveData<List<Run>>

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        val dao = RunningDatabase.getDatabse(application).getRunDao()
        repository = RunningRepository(dao)
        allRuns = repository.allRuns
        allRunsSortedByDate = repository.allRunsSortedByDate
        allRunsSortedByDateASC=repository.allRunsSortedByDateASC
        allRunsSortedByDistance = repository.allRunsSortedByDistance
        allRunsSortedByCalBurned = repository.allRunsSortedByCalBurned
        allRunsSortedByTimeInMillis = repository.allRunsSortedByTimeInMillis
        allRunsSortedByAvgSpeed = repository.allRunsSortedByAvgSpeed

        runs.addSource(allRunsSortedByDate) { result ->
            if (sortType == SortType.DATE) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(allRunsSortedByDistance) { result ->
            if (sortType == SortType.DISTANCE) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(allRunsSortedByAvgSpeed) { result ->
            if (sortType == SortType.AVG_SPEED) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(allRunsSortedByCalBurned) { result ->
            if (sortType == SortType.CALORIES_BURNED) {
                result?.let {
                    runs.value = it
                }
            }
        }

        runs.addSource(allRunsSortedByTimeInMillis) { result ->
            if (sortType == SortType.RUNNING_TIME) {
                result?.let {
                    runs.value = it
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> {
            allRunsSortedByDate.value?.let {
                runs.value = it
            }
        }

        SortType.AVG_SPEED -> {
            allRunsSortedByAvgSpeed.value?.let {
                runs.value = it
            }
        }

        SortType.CALORIES_BURNED -> {
            allRunsSortedByCalBurned.value?.let {
                runs.value = it
            }
        }

        SortType.DISTANCE -> {
            allRunsSortedByDistance.value?.let {
                runs.value = it
            }
        }

        SortType.RUNNING_TIME -> {
            allRunsSortedByTimeInMillis.value?.let {
                runs.value = it
            }
        }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRun(run)
    }

    fun deleteRun(run: List<Run>) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRun(run)
    }


}