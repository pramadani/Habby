package com.example.habby.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitEventDao {
    @Query("SELECT * FROM habit_events")
    fun getAllHabitEvents(): Flow<List<HabitEvent>>
    @Insert
    suspend fun insertHabitEvent(habitEvent: HabitEvent)
    @Query("SELECT * FROM habit_events WHERE habitId = :habitId ORDER BY eventStartTime DESC LIMIT 1")
    suspend fun getLatestHabitEventByHabitId(habitId: String): HabitEvent
    @Update
    suspend fun updateHabitEvent(habitEvent: HabitEvent)
}