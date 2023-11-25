package com.example.habby.view

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.habby.model.Habit
import com.example.habby.notification.Notification
import com.example.habby.viewmodel.HabitViewModel

@Composable
fun HabitPage(viewModel: HabitViewModel) {
    val habits = viewModel.habitList.collectAsState().value

    Column {
        Button(
            onClick = {
                viewModel.insertHabit(
                    Habit(name = "New Habit", description = "", frequency = 0)
                )
            }
        ) {
            Text("Add Habit")
        }


        val service = Notification(LocalContext.current)

        Button(onClick = { service.showNotification() }) {
            Text(text = "Show Notification")
        }

        HabitList(
            habits = habits
        )
    }

}

@Composable
fun HabitList(
    habits: List<Habit>,
) {
    LazyColumn {
        items(habits) { habit ->
            HabitItem(
                habit = habit
            )
        }
    }
}

@Composable
fun HabitItem(habit: Habit) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = habit.name)
            Text(text = habit.description)
        }
    }
}