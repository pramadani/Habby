package com.example.habby.viewmodel

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habby.model.Habit
import com.example.habby.model.HabitDao
import com.example.habby.model.HabitEvent
import com.example.habby.model.HabitEventDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.Time
import java.util.Date

class HabitViewModel(private val dao: HabitDao) : ViewModel() {

    private val _habitList = MutableStateFlow<List<Habit>>(emptyList())
    val habitList: StateFlow<List<Habit>> = _habitList

    private var selectedHabitID = ""

    init {
        viewModelScope.launch {
            dao.getAllHabits()
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
            dao.insertHabit(habit)
        }
    }

    fun getHabitById(habitId: String): Habit? {
        return dao.getHabitById(habitId)
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            dao.updateHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            dao.deleteHabit(habit)
        }
    }

    fun checkHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = true
            dao.updateHabit(habit)
        }
    }

    fun unCheckHabit(habit: Habit) {
        viewModelScope.launch {
            habit.isCheck = false
            dao.updateHabit(habit)
        }
    }

//    suspend fun startHabitEvent() {
//        val habitEvent = HabitEvent(
//            habitId = this.habitId,
//            dateTaken = java.sql.Date(Date().time), // Tanggal saat ini
//            eventStartTime = Time(Date().time), // Waktu saat ini
//            eventEndTime = null // Waktu kosong untuk sekarang
//        )
//
//        // Menambahkan habitEvent ke database menggunakan DAO
//        habitEventDao.insertHabitEvent(habitEvent)
//    }
//    suspend fun stopHabitEvent() {
//        // Mendapatkan habitEvent yang belum memiliki endTime terisi
//        var habitEvent = habitEventDao.getLatestHabitEventByHabitId(this.habitId)
//
//        if (habitEvent != null) {
//            // Mengupdate endTime habitEvent dengan waktu saat ini
//            habitEvent.eventEndTime = Time(Date().time)
//
//            // Melakukan update habitEvent di database menggunakan DAO
//            habitEventDao.updateHabitEvent(habitEvent)
//        }
//    }
}
