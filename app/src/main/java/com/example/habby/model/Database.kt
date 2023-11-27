package com.example.habby.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Habit::class, HabitEvent::class], version = 1)
abstract class HabbyDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}