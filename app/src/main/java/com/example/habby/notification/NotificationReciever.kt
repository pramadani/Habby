package com.example.habby.notification;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReciever: BroadcastReceiver()  {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = Notification(context)
    }
}
//