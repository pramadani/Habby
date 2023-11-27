package com.example.habby.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "habit_progress",
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["habitId"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitProgress(
    @PrimaryKey
    val habitProgressId: String = UUID.randomUUID().toString(),
    val habitId: String,
    val dateTaken: Long = Date().time,
    var progress: Boolean = false
)