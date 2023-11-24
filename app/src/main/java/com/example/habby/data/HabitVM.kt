package com.example.habby.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HabitViewModel(private val habitRepository: HabitRepository = HabitRepository()) : ViewModel() {

    // LiveData untuk mendapatkan daftar kebiasaan
    val allHabits: LiveData<List<Habit>> = habitRepository.allHabitsLiveData

    // Fungsi untuk memasukkan kebiasaan ke dalam database menggunakan coroutine
    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.insert(habit)
        }
    }

    // Fungsi untuk mengupdate kebiasaan di dalam database menggunakan coroutine
    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.update(habit)
        }
    }

    // Fungsi untuk menghapus kebiasaan dari database menggunakan coroutine
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            habitRepository.delete(habit)
        }
    }
}
