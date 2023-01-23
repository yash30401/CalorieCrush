package com.calories.running.track.caloriecrush.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Run::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RunningDatabase:RoomDatabase() {

    abstract fun getRunDao():RunDao


}