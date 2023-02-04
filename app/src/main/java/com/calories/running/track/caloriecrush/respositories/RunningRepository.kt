package com.calories.running.track.caloriecrush.respositories

import androidx.lifecycle.LiveData
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunDao

class RunningRepository(private val runDao: RunDao) {

    val allRuns:LiveData<List<Run>> = runDao.getAllRunsSortedByDistance()
    val allRunsSortedByDate:LiveData<List<Run>> = runDao.getAllRunsSortedByDate()
    val allRunsSortedByDistance:LiveData<List<Run>> = runDao.getAllRunsSortedByDistance()
    val allRunsSortedByCalBurned:LiveData<List<Run>> = runDao.getAllRunsSorteByCaloriesBurned()
    val allRunsSortedByTimeInMillis:LiveData<List<Run>> = runDao.getAllRunsSortedByTimeInMillis()
    val allRunsSortedByAvgSpeed:LiveData<List<Run>> = runDao.getAllRunsSortedByAverageSpeed()

    suspend fun insertRun(run:Run){
        runDao.insertRun(run)
    }

    suspend fun deleteRun(run: Run){
        runDao.deleteRun(run)
    }




}