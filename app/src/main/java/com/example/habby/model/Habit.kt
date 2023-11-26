package com.example.habby.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "habits",
)
data class Habit(
    @PrimaryKey
    val habitId: String = UUID.randomUUID().toString(),
    val name: String,
    val icon: String,
    val color: String,
    val dateStart: Long = Date().time,
    val time: String,
    val habitDuration: Int,
    val isEvent: Boolean = false,
    var isCheck: Boolean = false,
    val isDelayed: Boolean = false
)