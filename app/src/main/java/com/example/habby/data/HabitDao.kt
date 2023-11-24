package com.example.habby.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabitsLiveData(): LiveData<List<Habit>>


    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Long): Habit?

    @Insert
    fun insertHabit(habit: Habit): Long

    @Update
    fun updateHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)
}