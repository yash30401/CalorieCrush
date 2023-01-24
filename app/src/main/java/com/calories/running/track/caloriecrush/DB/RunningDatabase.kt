package com.calories.running.track.caloriecrush.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Run::class), version = 1, exportSchema = false)
abstract class RunningDatabase:RoomDatabase() {

    abstract fun getRunDao():RunDao


    companion object{
        @Volatile
        private var INSTANCE:RunningDatabase?=null

        fun getDatabse(context: Context):RunningDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    RunningDatabase::class.java,
                    "running_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }

}