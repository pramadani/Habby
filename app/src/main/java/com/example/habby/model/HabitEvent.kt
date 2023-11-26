package com.example.habby.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "habit_events",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["habitId"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitEvent(
    @PrimaryKey
    val habitEventId: String = UUID.randomUUID().toString(),
    val habitId: String,
    val eventStartTime: Long,
    var eventEndTime: Long?
)