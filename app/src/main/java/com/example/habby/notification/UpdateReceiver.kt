package com.example.habby.notification

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.habby.model.HabbyDatabase
import com.example.habby.model.HabitProgress
import com.example.habby.model.getDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MidnightUpdateReceiver : BroadcastReceiver() {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            // Panggil fungsi update di sini
            updateAllHabits(context)
        }
    }

    private fun updateAllHabits(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val habitDao = getDatabase(context).habitDao()
            val allHabits = habitDao.getAllHabitsOffline()

            for (habit in allHabits) {
                habit.isCheck = false

//                CoroutineScope(Dispatchers.IO).launch {
                    habitDao.updateHabit(habit)
                    habitDao.insertHabitProgress(
                        HabitProgress(habitId = habit.habitId)
                    )
//                }
            }
        }
    }
}