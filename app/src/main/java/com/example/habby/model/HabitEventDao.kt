package com.example.habby.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.sql.Time

@Dao
interface HabitEventDao {
    @Insert
    suspend fun insertHabitEvent(habitEvent: HabitEvent)
    @Query("SELECT * FROM habit_events WHERE habitId = :habitId ORDER BY dateTaken DESC LIMIT 1")
    fun getLatestHabitEventByHabitId(habitId: String): HabitEvent?
    @Update
    suspend fun updateHabitEvent(habitEvent: HabitEvent)
}