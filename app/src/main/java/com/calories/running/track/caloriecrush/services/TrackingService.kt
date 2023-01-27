package com.calories.running.track.caloriecrush.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.other.Constants.ACTION_PAUSE_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.calories.running.track.caloriecrush.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.ACTION_STOP_SERVICE
import com.calories.running.track.caloriecrush.other.Constants.NOTIFICATION_CHANNEL_ID
import com.calories.running.track.caloriecrush.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.calories.running.track.caloriecrush.other.Constants.NOTIFICATION_ID
import com.calories.running.track.caloriecrush.ui.MainActivity
import timber.log.Timber
import java.util.logging.LogRecord

class TrackingService : LifecycleService() {

    var isFirstRun=true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun){
                        startForegroundService()
                        isFirstRun=false
                    }else {
                        Log.d("Resuming Service...","Resume Service..")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused Service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped Service")
                }
                else -> {
                    Log.d("Nothing", "Nothing")
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)

    }

    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.baseline_directions_run_24)
            .setContentTitle("Calorie Crush")
            .setContentText("00:00:00")
            .setContentIntent(getMainAcitivtyPendingIntent())

        startForeground(NOTIFICATION_ID,notificationBuilder.build())

    }

    private fun getMainAcitivtyPendingIntent()=PendingIntent.getActivity(
        this,
        0,
        Intent(this,MainActivity::class.java).also {
            it.action=ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,


    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)

        notificationManager.createNotificationChannel(channel)

    }

}