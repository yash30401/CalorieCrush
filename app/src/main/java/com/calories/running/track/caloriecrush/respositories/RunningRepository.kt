package com.calories.running.track.caloriecrush.respositories

import androidx.lifecycle.LiveData
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunDao

class RunningRepository(private val runDao: RunDao) {

    val allRuns:LiveData<List<Run>> = runDao.getAllRunsSortedByDistance()

    suspend fun insertRun(run:Run){
        runDao.insertRun(run)
    }

    suspend fun deleteRun(run: Run){
        runDao.deleteRun(run)
    }

     fun getAllRunsSortedByDate(){
        runDao.getAllRunsSortedByDate()
    }

     fun getAllRunsSortedByTimeInMillis(){
        runDao.getAllRunsSortedByTimeInMillis()
    }

    fun getAllRunsSorteByCaloriesBurned(){
        runDao.getAllRunsSorteByCaloriesBurned()
    }

    fun getAllRunsSortedByAverageSpeed(){
        runDao.getAllRunsSortedByAverageSpeed()
    }

    fun getAllRunsSortedByDistance(){
        runDao.getAllRunsSortedByDistance()
    }

    fun getTotalTimeInMillis(){
        runDao.getTotalTimeInMillis()
    }

    fun getTotalCaloriesBurned(){
        runDao.getTotalCaloriesBurned()
    }

    fun getTotalDistance(){
        runDao.getTotalDistance()
    }

    fun getTotalAverageSpeed(){
        runDao.getTotalAverageSpeed()
    }
}