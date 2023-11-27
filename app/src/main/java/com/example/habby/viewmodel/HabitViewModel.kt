package com.example.habby.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habby.model.Habit
import com.example.habby.model.HabitDao
import com.example.habby.model.HabitEvent
import com.example.habby.model.HabitEventDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class HabitViewModel(private val habitDao: HabitDao, private val habitEventDao: HabitEventDao) : ViewModel() {

    private val _habitList = MutableStateFlow<List<Habit>>(emptyList())
    val habitList: StateFlow<List<Habit>> = _habitList

    private val _habitEventList = MutableStateFlow<List<HabitEvent>>(emptyList())
    val habitEventList: StateFlow<List<HabitEvent>> = _habitEventList

    private lateinit var selectedHabit: Habit

    init {
        viewModelScope.launch {
            habitDao.getAllHabits()
                .collect { habits ->
                    _habitList.value = habits
                    Log.d("woe", "halos")
                }
        }
        viewModelScope.launch {
            habitEventDao.getAllHabitEvents()
                .collect { events ->
                    _habitEventList.value = events
                    Log.d("TAst", "halo")
                }
        }
    }

    fun getHabitEventByHabitId(habitId: String): HabitEvent? {
        return habitEventList.value.firstOrNull { it.habitId == habitId }
    }

    fun getHabitCheckByHabitId(habitId: String): Habit {
        return habitList.value.first { it.habitId == habitId }
    }

    fun setSelectedHabit(habit: Habit) {
        selectedHabit = habit
    }

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
    }

    fun getHabitById(habitId: String): Habit {
            return habitDao.getHabitById(habitId)

    }

    fun getSelectedHabit(): Habit {
        return selectedHabit
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

    fun checkHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = true
            habitDao.updateHabit(habit)

            val currentList = _habitList.value.toMutableList()
            val index = currentList.indexOfFirst { it.habitId == habit.habitId }

            if (index != -1) {
                currentList[index] = habit.copy()
                _habitList.value = currentList
            }
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
                habitId = habit.habitId,
                eventStartTime = Date().time,
                eventEndTime = null
            )

            habit.isCheck = true
            habitDao.updateHabit(habit)
            habitEventDao.insertHabitEvent(habitEvent)
        }
    }
    fun stopHabitEvent(habit: Habit) {
        viewModelScope.launch {

            val habitEvent = habitEventDao.getLatestHabitEventByHabitId(habit.habitId)

            habitEvent.eventEndTime = Date().time

            habitEventDao.updateHabitEvent(habitEvent)
        }
    }
}
