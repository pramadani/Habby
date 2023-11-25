package com.example.habby.model

import android.content.Context
import androidx.room.Room

fun getDatabase(context: Context): HabbyDatabase {
    val db by lazy {
        Room.databaseBuilder(
            context,
            HabbyDatabase::class.java,
            "habby.db"
        ).build()
    }
    return db
}
