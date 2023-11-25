package com.example.habby.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time
import java.time.Duration
import java.util.UUID
import java.util.concurrent.Delayed

@Entity(
    tableName = "habits",
)
data class Habit(
    @PrimaryKey
    val habitId: String = UUID.randomUUID().toString(),
    val name: String,
    val icon: String,
    val color: String,
    val dateStart: Date,
    val time: Time,
    val habitDuration: Int,
    val isEvent: Boolean,
    val isCheck: Boolean,
    val isDelayed: Boolean
)