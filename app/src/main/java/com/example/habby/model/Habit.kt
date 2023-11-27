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
    var name: String,
    var icon: String,
    var color: String,
    var interval: String,
    val dateStart: Long = Date().time,
    var time: String,
    var habitDuration: Int,
    var isEvent: Boolean = false,
    var isCheck: Boolean = false,
    val isDelayed: Boolean = false
)