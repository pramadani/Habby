package com.example.habby.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.time.LocalTime
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

//fun setAlarm(context: Context, selectedDays: String, notificationTimeInMillis: Long) {
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
//    // Atur waktu alarm pada pukul 17:05
//    val calendar = Calendar.getInstance().apply {
//        timeInMillis = System.currentTimeMillis()
//        set(Calendar.HOUR_OF_DAY, 18)
//        set(Calendar.MINUTE, 12)
//        set(Calendar.SECOND, 0)
//    }
//
//    // Pastikan waktu yang diatur belum lewat, jika iya, tambahkan satu hari
//    if (calendar.before(Calendar.getInstance())) {
//        calendar.add(Calendar.DAY_OF_MONTH, 1)
//    }
//
//    // Interval waktu untuk pengulangan alarm setiap hari
//    val intervalMillis = 7 * 24 * 60 * 60 * 1000L  // 24 jam dalam milidetik
//
//    // Set alarm menggunakan AlarmManager dengan metode setRepeating
//    alarmManager.setRepeating(
//        AlarmManager.RTC_WAKEUP,
//        calendar.timeInMillis,
//        intervalMillis,
//        pendingIntent
//    )
//}

fun setAlarm(context: Context, selectedDays: String, notificationTime: LocalTime) {
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

    // Atur waktu alarm sesuai dengan LocalTime
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, notificationTime.hour)
        set(Calendar.MINUTE, notificationTime.minute)
        set(Calendar.SECOND, 0)
    }

    // Set hari alarm berulang
    val selectedDayList = selectedDays.split("-").filter { it.isNotBlank() }
    val dayOfWeek = selectedDayList.map { parseDayOfWeek(it) }.toIntArray()

    // Set interval waktu untuk pengulangan alarm setiap minggu
    val intervalMillis = 24 * 60 * 60 * 1000L  // 7 hari dalam milidetik

    // Set alarm menggunakan AlarmManager dengan metode setRepeating
    for (day in dayOfWeek) {
//        val nextAlarmTime = getNextAlarmTime(calendar, day)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            intervalMillis,
            pendingIntent
        )
    }
}

fun getNextAlarmTime(calendar: Calendar, selectedDay: Int): Long {
    calendar.set(Calendar.DAY_OF_WEEK, selectedDay)

    // Jika waktu alarm sudah lewat untuk hari yang dipilih, tambahkan 7 hari (minggu berikutnya)
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_MONTH, 7)
    }

    // Atur waktu sesuai jam dan menit yang ditentukan pada notificationTimeInMillis
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.SECOND, 0)

    return calendar.timeInMillis
}

fun parseDayOfWeek(day: String): Int {
    return when (day.uppercase(Locale.ROOT)) {
        "SUN" -> Calendar.SUNDAY
        "MON" -> Calendar.MONDAY
        "TUE" -> Calendar.TUESDAY
        "WED" -> Calendar.WEDNESDAY
        "THU" -> Calendar.THURSDAY
        "FRI" -> Calendar.FRIDAY
        "SAT" -> Calendar.SATURDAY
        else -> throw IllegalArgumentException("Invalid day of week: $day")
    }
}