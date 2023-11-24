package com.example.habby.data

import android.content.Context
import androidx.lifecycle.LiveData

class HabitRepository(context: Context) {
    private val habitDao: HabitDao = HabitDatabase.getInstance(context).habitDao()

    val allHabitsLiveData: LiveData<List<Habit>> = habitDao.getAllHabitsLiveData()

    suspend fun insert(habit: Habit): Long {
        return habitDao.insertHabit(habit)
    }

    suspend fun update(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun delete(habit: Habit) {
        habitDao.deleteHabit(habit)
    }
}