package com.example.habby.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Habit::class,
        HabitEvent::class,
        HabitProgress::class
               ],
    version = 1
)
abstract class HabbyDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

}