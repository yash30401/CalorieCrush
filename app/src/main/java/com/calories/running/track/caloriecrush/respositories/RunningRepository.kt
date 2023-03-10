package com.calories.running.track.caloriecrush.respositories

import androidx.lifecycle.LiveData
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.DB.RunDao

class RunningRepository(private val runDao: RunDao) {

    val allRuns:LiveData<List<Run>> = runDao.getAllRunsSortedByDistance()
    val allRunsSortedByDate:LiveData<List<Run>> = runDao.getAllRunsSortedByDate()
    val allRunsSortedByDateASC:LiveData<List<Run>> = runDao.getAllRunsSortedByDateASC()
    val allRunsSortedByDistance:LiveData<List<Run>> = runDao.getAllRunsSortedByDistance()
    val allRunsSortedByCalBurned:LiveData<List<Run>> = runDao.getAllRunsSorteByCaloriesBurned()
    val allRunsSortedByTimeInMillis:LiveData<List<Run>> = runDao.getAllRunsSortedByTimeInMillis()
    val allRunsSortedByAvgSpeed:LiveData<List<Run>> = runDao.getAllRunsSortedByAverageSpeed()
    val getTotalTimeInMillis:LiveData<Long> = runDao.getTotalTimeInMillis()
    val getTotalCaloriesBurned:LiveData<Int> = runDao.getTotalCaloriesBurned()
    val getTotalDistance:LiveData<Int> = runDao.getTotalDistance()
    val getTotalAverageSpeed:LiveData<Float> = runDao.getTotalAverageSpeed()


    suspend fun insertRun(run:Run){
        runDao.insertRun(run)
    }

    suspend fun deleteRun(run:List<Run>){
        runDao.deleteRun(run)
    }




}