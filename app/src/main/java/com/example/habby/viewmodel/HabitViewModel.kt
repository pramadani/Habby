package com.example.habby.viewmodel

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

//    private val _habitEventList = MutableStateFlow<List<Habit>>(emptyList())
//    val habitEventList: StateFlow<List<Habit>> = _habitEventList

    private var selectedHabitID = ""

    init {
        viewModelScope.launch {
            habitDao.getAllHabits()
                .collect { habits ->
                    _habitList.value = habits
                }

        }
    }

    fun setSelectedHabit(habitID: String) {
        selectedHabitID = habitID
    }

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitDao.insertHabit(habit)
        }
    }

    fun getHabitById(habitId: String): Habit? {
        return habitDao.getHabitById(habitId)
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

            var habitEvent = habitEventDao.getLatestHabitEventByHabitId(habit.habitId)!!

            habitEvent.eventEndTime = Date().time

            habitEventDao.updateHabitEvent(habitEvent)
        }
    }
}
