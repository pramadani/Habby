package com.example.habby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habby.model.Habit
import com.example.habby.model.HabitDao
import com.example.habby.model.HabitEvent
import com.example.habby.model.HabitProgress
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {

    val habitList = habitDao.getAllHabits().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val habitEventList = habitDao.getAllHabitEvents().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val habitProgressList = habitDao.getAllHabitProgress().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private lateinit var selectedHabit: Habit

    fun getHabitEventByHabitId(habitId: String): HabitEvent? {
        return habitEventList.value.firstOrNull { it.habitId == habitId }
    }
    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
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


    fun checkHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = true
            habitDao.updateHabit(habit)
        }
    }
    fun unCheckHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = false
            habitDao.updateHabit(habit)
        }
    }

    fun startHabitEvent(habit: Habit) {
        viewModelScope.launch {
            val habitEvent = HabitEvent(
                habitId = habit.habitId
            )
            habit.isCheck = true
            habitDao.updateHabit(habit)
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
            val habitProgress = HabitProgress(
                habitId = habit.habitId
            )
            habitDao.insertHabitProgress(habitProgress)
        }
    }
}
