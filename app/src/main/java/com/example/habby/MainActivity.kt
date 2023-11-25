package com.example.habby

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habby.model.getDatabase
import com.example.habby.navigation.NavLayout
import com.example.habby.notification.Notification
import com.example.habby.ui.theme.HabbyTheme
import com.example.habby.viewmodel.HabitViewModel


@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    private val appContext = applicationContext
    private val habitViewModel by viewModels<HabitViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HabitViewModel(getDatabase(applicationContext).habitDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabbyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavLayout(viewModel = habitViewModel)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Notification.channelID,
                "channel1",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = Notification.content

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}