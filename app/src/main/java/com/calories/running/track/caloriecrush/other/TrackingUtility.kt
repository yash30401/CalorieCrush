package com.calories.running.track.caloriecrush.other

import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

object TrackingUtility {

    fun getFormattedStopwatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if (!includeMillis) {
            return "${if (hours < 10) "0" else ""}$hours:" +
                    "${if (minutes < 10) "0" else ""}$minutes:" +
                    "${if (seconds < 10) "0" else ""}$seconds"
        }


        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if (hours < 10)"0" else ""}$hours:" +
                "${if (minutes < 10)"0" else ""}$minutes:" +
                "${if (seconds < 10)"0" else ""}$seconds:" +
                "${if (milliseconds < 10)"0" else ""}$milliseconds"
    }
}