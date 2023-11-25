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
import java.sql.Time
import java.util.Date

class HabitViewModel(private val dao: HabitDao) : ViewModel() {

    private val _habitList = MutableStateFlow<List<Habit>>(emptyList())
    val habitList: StateFlow<List<Habit>> = _habitList

    init {
        viewModelScope.launch {
            dao.getAllHabits()
                .collect { habits ->
                    _habitList.value = habits
                }
        }
    }

    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            dao.insertHabit(habit)
        }
    }

//    suspend fun startHabitEvent(habitEventDao: HabitEventDao) {
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
//    suspend fun stopHabitEvent(habitEventDao: HabitEventDao) {
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
