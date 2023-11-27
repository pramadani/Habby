package com.example.habby.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.habby.model.Habit
import com.example.habby.model.HabitEvent
import com.example.habby.viewmodel.HabitViewModel

@Composable
fun HabitPage(viewModel: HabitViewModel, navController: NavController) {
    Column {
        Button(
            onClick = {
                navController.navigate("CreateMenuPage")
            }
        ) {
            Text("Add Habit")
        }
            HabitList(viewModel, navController)
    }

}
@Composable
fun HabitList(viewModel: HabitViewModel, navController: NavController) {
    val habits = viewModel.habitList.collectAsState().value
    viewModel.habitEventList.collectAsState().value
    LazyColumn {
        items(habits) { habit ->

            val habitEvent = viewModel.getHabitEventByHabitId(habit.habitId)
            Column {
                HabitItem(habit, habitEvent, viewModel, navController)
                Spacer(modifier = Modifier.height(25.dp))
            }

        }
    }
}

@Composable
fun HabitItem(habit: Habit, habitEvent: HabitEvent?, viewModel: HabitViewModel, navController: NavController) {
    Row {
        Column(
            modifier = Modifier
                .clickable {
                    viewModel.setSelectedHabit(habit)
                    navController.navigate("EditHabitFormPage")
                }
        ) {
            Text(text = habit.name)
            Text(text = habit.color)
            Text(text = habit.icon)
            Text(text = habit.time)
            Text(text = habit.habitDuration.toString())
            Text(text = habit.isCheck.toString())
            Text(text = habit.isDelayed.toString())
            Text(text = habit.isEvent.toString())

            if (habitEvent != null) {
                Text(text = habitEvent.eventStartTime.toString())
            } else {
                Text("null")
            }

            if (habitEvent != null) {
                Text(text = habitEvent.eventEndTime.toString())
            } else {
                Text("null")
            }

            var isChecked by remember { mutableStateOf(habit.isCheck) }

            RadioButton(
                selected = isChecked,
                onClick = {
                    if (isChecked == false) {
                        isChecked = true
                        viewModel.checkHabit(habit)
                    } else {
                        isChecked = false
                        viewModel.unCheckHabit(habit)
                    }
                }
            )

            Button(
                onClick = {
                    isChecked = true
                    viewModel.startHabitEvent(habit)
                }
            ) {
                Text(text = "Start")
            }

            Button(
                onClick = {
                    viewModel.stopHabitEvent(habit)
                }
            ) {
                Text(text = "Stop")
            }
        }
    }
}