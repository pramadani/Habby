package com.example.habby.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time
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
    val dateTaken: Long,
    val eventStartTime: Long,
    var eventEndTime: Long?
)