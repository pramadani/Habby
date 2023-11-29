package com.example.habby.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>
    @Insert
    suspend fun insertHabit(habit: Habit)
    @Update
    suspend fun updateHabit(habit: Habit)
    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits")
    fun getAllHabitsOffline(): List<Habit>

    @Query("SELECT * FROM habit_events")
    fun getAllHabitEvents(): Flow<List<HabitEvent>>
    @Insert
    suspend fun insertHabitEvent(habitEvent: HabitEvent)
    @Query("SELECT * FROM habit_events WHERE habitId = :habitId ORDER BY eventStartTime DESC LIMIT 1")
    suspend fun getLatestHabitEventByHabitId(habitId: String): HabitEvent
    @Update
    suspend fun updateHabitEvent(habitEvent: HabitEvent)


    @Query("SELECT * FROM habit_progress")
    fun getAllHabitProgress(): Flow<List<HabitProgress>>
    @Insert
    suspend fun insertHabitProgress(habitProgress: HabitProgress)
    @Query("SELECT * FROM habit_progress WHERE habitId = :habitId ORDER BY dateTaken DESC LIMIT 1")
    suspend fun getLatestHabitProgressByHabitId(habitId: String): HabitProgress
    @Update
    suspend fun updateHabitProgress(habitProgress: HabitProgress)
}