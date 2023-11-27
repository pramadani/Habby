package com.example.habby.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar
import java.util.Locale

@SuppressLint("ServiceCast", "ScheduleExactAlarm")
//fun setAlarm(context: Context) {
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val intent = Intent(context, AlarmReceiver::class.java)
//    val pendingIntent =
//        PendingIntent.getBroadcast(
//            context,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                PendingIntent.FLAG_MUTABLE
//            } else {
//                0
//            }
//        )
//
//    val calendar = Calendar.getInstance()
//    calendar.set(2023, Calendar.NOVEMBER, 27, 14, 16) // 1 Januari 2023, jam 8 malam
//
//    // Set alarm menggunakan AlarmManager
//    alarmManager.setExact(
//        AlarmManager.RTC_WAKEUP,
//        calendar.timeInMillis,
//        pendingIntent
//    )
//}

//fun setAlarm(context: Context, selectedDays: String, notificationTimeInMillis: Long) {
//    Log.d("testasas", "testttt")
//
//    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//    val intent = Intent(context, AlarmReceiver::class.java)
//    val pendingIntent = PendingIntent.getBroadcast(
//        context,
//        0,
//        intent,
//        PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            PendingIntent.FLAG_MUTABLE
//        } else {
//            0
//        }
//    )
//
//    val calendar = Calendar.getInstance()
//    calendar.timeInMillis = System.currentTimeMillis()
//
//    // Mendapatkan daftar hari dari input
//    val selectedDayList = selectedDays.split("-")
//
//    // Set waktu alarm pada waktu yang ditentukan
//    calendar.set(2023, Calendar.NOVEMBER, 27, 17, 1)
//
//    // Set alarm pada hari pertama dalam daftar
//    val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
//    val daysUntilNextSelectedDay = selectedDayList
//        .map { parseDayOfWeek(it) }
//        .map { (it - currentDayOfWeek + 7) % 7 }
//        .minOrNull()
//
//    if (daysUntilNextSelectedDay != null) {
//        calendar.add(Calendar.DAY_OF_WEEK, daysUntilNextSelectedDay)
//    }
//
//    // Set alarm menggunakan AlarmManager dan atur agar diulang setiap minggu
//    alarmManager.setRepeating(
//        AlarmManager.RTC_WAKEUP,
//        calendar.timeInMillis,
//        AlarmManager.INTERVAL_DAY * 7,
//        pendingIntent
//    )
//
//}

fun setAlarm(context: Context, selectedDays: String, notificationTimeInMillis: Long) {
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

    // Atur waktu alarm pada pukul 17:05
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 17)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 0)
    }

    // Pastikan waktu yang diatur belum lewat, jika iya, tambahkan satu hari
    if (calendar.before(Calendar.getInstance())) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    // Interval waktu untuk pengulangan alarm setiap hari
    val intervalMillis = 24 * 60 * 60 * 1000L  // 24 jam dalam milidetik

    // Set alarm menggunakan AlarmManager dengan metode setRepeating
    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        intervalMillis,
        pendingIntent
    )
}

fun parseDayOfWeek(day: String): Int {
    return when (day.uppercase(Locale.ROOT)) {
        "MON" -> Calendar.MONDAY
        "TUE" -> Calendar.TUESDAY
        "WED" -> Calendar.WEDNESDAY
        "THU" -> Calendar.THURSDAY
        "FRI" -> Calendar.FRIDAY
        "SAT" -> Calendar.SATURDAY
        "SUN" -> Calendar.SUNDAY
        else -> throw IllegalArgumentException("Invalid day of week: $day")
    }
}