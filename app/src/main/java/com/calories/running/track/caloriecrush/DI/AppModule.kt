package com.calories.running.track.caloriecrush.DI

import android.content.Context
import androidx.room.Room
import com.calories.running.track.caloriecrush.DB.RunningDatabase
import com.calories.running.track.caloriecrush.DI.Constants.RUNNING_DATABSE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,RunningDatabase::class.java,RUNNING_DATABSE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db:RunningDatabase)=db.getRunDao()

}