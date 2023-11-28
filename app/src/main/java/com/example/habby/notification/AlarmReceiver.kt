package com.example.habby.notification

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.habby.R

class AlarmReceiver: BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context)
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context?) {
        val builder = NotificationCompat.Builder(context!!, "channel1")
            .setContentTitle("Habit Reminder")
            .setContentText("It's time to do today habit!")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ganti dengan ikon aplikasi Anda

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }

}