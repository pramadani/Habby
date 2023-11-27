package com.example.habby.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habby.model.Habit
import com.example.habby.model.HabitDao
import com.example.habby.model.HabitEvent
import com.example.habby.model.HabitProgress
import com.example.habby.notification.setAlarm
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Date

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {

    val habitList = habitDao.getAllHabits().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitEventList = habitDao.getAllHabitEvents().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitProgressList = habitDao.getAllHabitProgress().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private lateinit var selectedHabit: Habit

    fun getHabitEventByHabitId(habitId: String): HabitEvent? {
        return habitEventList.value.firstOrNull { it.habitId == habitId }
    }
    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
            insertHabitProgress(habit)
        }
    }

    fun scheduleNotification(context: Context, interval: String, Time: LocalTime) {
        setAlarm(context, interval, Time)
        Log.d("te", "testttt546")
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.updateHabit(habit)
        }
    }
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.deleteHabit(habit)
        }
    }


    fun setSelectedHabit(habit: Habit) {
        selectedHabit = habit
    }
    fun getSelectedHabit(): Habit {
        return selectedHabit
    }

//
    fun checkHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = true
            updateHabitProgress(habit)
            habitDao.updateHabit(habit)
        }
    }
    fun unCheckHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = false
            updateHabitProgress(habit)
            habitDao.updateHabit(habit)
        }
    }

    fun startHabitEvent(habit: Habit) {
        viewModelScope.launch {
            val habitEvent = HabitEvent(
                habitId = habit.habitId
            )
            checkHabit(habit)
            habitDao.insertHabitEvent(habitEvent)
        }
    }
    fun stopHabitEvent(habit: Habit) {
        viewModelScope.launch {
            val habitEvent = habitDao.getLatestHabitEventByHabitId(habit.habitId)
            habitEvent.eventEndTime = Date().time
            habitDao.updateHabitEvent(habitEvent)
        }
    }

    fun insertHabitProgress(habit: Habit) {
        viewModelScope.launch {
            val habitProgress = HabitProgress(
                habitId = habit.habitId
            )
            habitDao.insertHabitProgress(habitProgress)
        }
    }

    fun updateHabitProgress(habit: Habit) {
        viewModelScope.launch {
            val habitProgress = habitDao.getLatestHabitProgressByHabitId(habit.habitId)
            habitProgress.progress = habit.isCheck
            habitDao.updateHabitProgress(habitProgress)
        }
    }
}
