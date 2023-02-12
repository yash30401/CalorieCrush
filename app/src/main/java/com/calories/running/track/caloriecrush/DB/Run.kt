package com.calories.running.track.caloriecrush.DB

import android.graphics.Bitmap
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Keep
@Entity(tableName = "running_table")
@TypeConverters(Converters::class)
data class Run(
    var img: Bitmap? = null,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}