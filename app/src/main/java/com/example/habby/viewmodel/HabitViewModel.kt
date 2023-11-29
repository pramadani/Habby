package com.example.habby.viewmodel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {

    val habitList = habitDao.getAllHabits().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitEventList = habitDao.getAllHabitEvents().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitProgressList = habitDao.getAllHabitProgress().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private lateinit var selectedHabit: Habit

    fun getHabitProgress(habitID: String): List<HabitProgress> {
        val filteredList = habitProgressList.value.filter { it.habitId == habitID }
        return filteredList
    }

    fun getStatisticAllAverageDuration(): Int {
        val totalDurationInHours = getStatisticAllTotalDuration()

        val totalHabits = habitProgressList.value.size

        return if (totalHabits > 0) {
            totalDurationInHours / totalHabits
        } else {
            0
        }
    }
    fun getStatisticAllTotalDuration(): Int {
        var totalDurationInMinutes = 0

        val habitEventMap = habitEventList.value.groupBy { it.habitId }

        val habitProgressMap = habitProgressList.value.groupBy { it.habitId }

        for (habit in habitList.value) {
            val habitId = habit.habitId

            val habitEventDurationInMinutes = habitEventMap[habitId]?.sumBy {
                val endTime = it.eventEndTime
                val startTime = it.eventStartTime

                if (endTime != null && startTime != null) {
                    TimeUnit.MILLISECONDS.toMinutes(endTime - startTime).toInt()
                } else {
                    0
                }
            } ?: 0

            val habitProgressCount = habitProgressMap[habitId]?.size ?: 0
            val habitEventCount = habitEventMap[habitId]?.size ?: 0

            val adjustedProgressCount = habitProgressCount - habitEventCount

            totalDurationInMinutes += adjustedProgressCount * habit.habitDuration + habitEventDurationInMinutes
        }

        val totalDurationInHours = TimeUnit.MINUTES.toHours(totalDurationInMinutes.toLong()).toInt()

        return totalDurationInHours
    }

    fun getStatisticAllEveryProgress(): Int {
        return habitProgressList.value.size
    }

    fun getStatisticAllTrueProgress(): Int {
        return habitProgressList.value.count { it.progress }
    }

    fun getStatisticAllPercentage(): Int {
        val totalProgress = getStatisticAllEveryProgress()
        val trueProgress = getStatisticAllTrueProgress()

        return if (totalProgress > 0) {
            (trueProgress * 100) / totalProgress
        } else {
            0
        }
    }

    fun getStatisticAllStreak(): Int {
        if (habitProgressList.value.isEmpty()) return 0

        var currentStreak = 0
        var maxStreak = 0
        var currentHabitId: String? = null

        for (progress in habitProgressList.value) {
            if (progress.progress && progress.habitId == currentHabitId) {
                currentStreak++
            } else {
                maxStreak = maxOf(maxStreak, currentStreak)
                currentStreak = if (progress.progress) 1 else 0
                currentHabitId = progress.habitId
            }
        }

        return maxOf(maxStreak, currentStreak)
    }


    fun getStatisticCurrentAllStreak(): Int {
        if (habitProgressList.value.isEmpty()) return 0

        var currentStreak = 0
        var currentHabitId: String? = null

        for (i in habitProgressList.value.size - 1 downTo 0) {
            val progress = habitProgressList.value[i]

            if (progress.progress && (currentHabitId == null || progress.habitId == currentHabitId)) {
                currentStreak++
            } else if (currentStreak > 0) {
                break
            }

            currentHabitId = progress.habitId
        }

        return currentStreak
    }



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
