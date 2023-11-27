package com.example.habby.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitEventDao {
    @Insert
    suspend fun insertHabitEvent(habitEvent: HabitEvent)
    @Query("SELECT * FROM habit_events WHERE habitId = :habitId ORDER BY eventStartTime DESC LIMIT 1")
    suspend fun getLatestHabitEventByHabitId(habitId: String): HabitEvent?
    @Update
    suspend fun updateHabitEvent(habitEvent: HabitEvent)
}