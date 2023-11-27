package com.example.habby.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

@SuppressLint("ServiceCast", "ScheduleExactAlarm")
fun setAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_MUTABLE
            } else {
                0
            }
        )

    val calendar = Calendar.getInstance()
    calendar.set(2023, Calendar.NOVEMBER, 27, 14, 16) // 1 Januari 2023, jam 8 malam

    // Set alarm menggunakan AlarmManager
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}