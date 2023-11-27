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
import java.util.concurrent.TimeUnit

class HabitViewModel(private val habitDao: HabitDao) : ViewModel() {

    val habitList = habitDao.getAllHabits().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitEventList = habitDao.getAllHabitEvents().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val habitProgressList = habitDao.getAllHabitProgress().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private lateinit var selectedHabit: Habit

    fun getStatisticAllAverageDuration(): Int {
        // Menghitung total durasi menggunakan fungsi yang sudah dibuat sebelumnya
        val totalDurationInHours = getStatisticAllTotalDuration()

        // Menghitung jumlah habit
        val totalHabits = habitList.value.size

        // Menghitung rata-rata durasi
        return if (totalHabits > 0) {
            totalDurationInHours / totalHabits
        } else {
            0
        }
    }
    fun getStatisticAllTotalDuration(): Int {
        // Menginisialisasi total durasi
        var totalDurationInMinutes = 0

        // Membuat map untuk mengelompokkan habitEvent berdasarkan habitId
        val habitEventMap = habitEventList.value.groupBy { it.habitId }

        // Membuat map untuk mengelompokkan habitProgress berdasarkan habitId
        val habitProgressMap = habitProgressList.value.groupBy { it.habitId }

        // Loop melalui habitList untuk menghitung total durasi
        for (habit in habitList.value) {
            val habitId = habit.habitId

            // Menghitung durasi dari habitEvent dengan habitId yang sama
            val habitEventDurationInMinutes = habitEventMap[habitId]?.sumBy {
                val endTime = it.eventEndTime
                val startTime = it.eventStartTime

                if (endTime != null && startTime != null) {
                    TimeUnit.MILLISECONDS.toMinutes(endTime - startTime).toInt()
                } else {
                    0
                }
            } ?: 0

            // Menghitung jumlah habit progress dan habit event yang mempunyai habitId yang sama
            val habitProgressCount = habitProgressMap[habitId]?.size ?: 0
            val habitEventCount = habitEventMap[habitId]?.size ?: 0

            // Jika jumlah habit progress lebih banyak dari jumlah habit event, kurangkan
            val adjustedProgressCount = habitProgressCount - habitEventCount

            // Menambahkan durasi dari habitEvent dan adjusted progress ke total durasi (dalam menit)
            totalDurationInMinutes += adjustedProgressCount * habit.habitDuration + habitEventDurationInMinutes
        }

        // Mengonversi total durasi dari menit ke jam
        val totalDurationInHours = TimeUnit.MINUTES.toHours(totalDurationInMinutes.toLong()).toInt()

        return totalDurationInHours
    }

    fun getStatisticAllEveryProgress(): Int {
        // Mengembalikan jumlah total habit progress
        return habitProgressList.value.size
    }

    fun getStatisticAllTrueProgress(): Int {
        // Menghitung jumlah habit progress yang bernilai true
        return habitProgressList.value.count { it.progress }
    }

    fun getStatisticAllPercentage(): Int {
        // Menghitung persentase habit progress yang bernilai true
        val totalProgress = getStatisticAllEveryProgress()
        val trueProgress = getStatisticAllTrueProgress()

        // Menghindari pembagian oleh nol
        return if (totalProgress > 0) {
            (trueProgress * 100) / totalProgress
        } else {
            0
        }
    }

    fun getStatisticAllStreak(): Int {
        // Memastikan habitProgressList tidak kosong
        if (habitProgressList.value.isEmpty()) return 0

        var currentStreak = 0
        var maxStreak = 0
        var currentHabitId: String? = null

        // Loop melalui habit progress list
        for (progress in habitProgressList.value) {
            if (progress.progress && progress.habitId == currentHabitId) {
                // Jika progress true dan habitId sama, tambahkan ke streak saat ini
                currentStreak++
            } else {
                // Jika progress false atau habitId berbeda, reset streak saat ini
                maxStreak = maxOf(maxStreak, currentStreak)
                currentStreak = if (progress.progress) 1 else 0
                currentHabitId = progress.habitId
            }
        }

        // Mengembalikan streak tertinggi
        return maxOf(maxStreak, currentStreak)
    }


    fun getStatisticCurrentAllStreak(): Int {
        // Memastikan habitProgressList tidak kosong
        if (habitProgressList.value.isEmpty()) return 0

        var currentStreak = 0
        var currentHabitId: String? = null

        // Loop melalui habit progress list dari belakang
        for (i in habitProgressList.value.size - 1 downTo 0) {
            val progress = habitProgressList.value[i]

            if (progress.progress && (currentHabitId == null || progress.habitId == currentHabitId)) {
                // Jika progress true dan habitId sama atau currentHabitId belum diatur, tambahkan ke current streak
                currentStreak++
            } else if (currentStreak > 0) {
                // Jika progress false dan current streak sudah dimulai, hentikan loop
                break
            }

            currentHabitId = progress.habitId
        }

        // Mengembalikan current streak
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
