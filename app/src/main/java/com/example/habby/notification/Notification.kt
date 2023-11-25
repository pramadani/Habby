package com.example.habby.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.habby.MainActivity
import com.example.habby.R

class Notification(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object{
        const val notificationID = 1
        const val channelID = "channel1"
        const val keyID = 1
        const val title = "Title"
        const val content = "Content"
        const val date = "2023-11-25"
        const val time = "12:30 PM"
    }
    fun showNotification(context: Context, intent: Intent) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(
            notificationID,
            notification
        )

//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationID, notification)
    }

//    override fun onReceive(context: Context, intent: Intent) {
//        val notification = NotificationCompat.Builder(context, channelID)
////            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setContentTitle(intent.getStringExtra(title))
//            .setContentText(intent.getStringExtra(content))
//            .build()
//
//        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        manager.notify(notificationID, notification)
//    }

}