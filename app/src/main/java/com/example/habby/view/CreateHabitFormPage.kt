package com.example.habby.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.habby.model.Habit
import com.example.habby.viewmodel.HabitViewModel
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHabitFormPage(viewModel: HabitViewModel, navController: NavHostController) {
    var habitName by remember { mutableStateOf("") }
    var habitIcon by remember { mutableStateOf("") }
    var habitColor by remember { mutableStateOf("") }
    var habitTimeHour by remember { mutableStateOf("") }
    var habitTimeMinute by remember { mutableStateOf("") }
    var habitDuration by remember { mutableStateOf("") }
    var isEvent by remember { mutableStateOf(false) }

    Column{
        OutlinedTextField(
            value = habitName,
            onValueChange = { habitName = it },
            label = { Text("Habit Name") }
        )

        OutlinedTextField(
            value = habitIcon,
            onValueChange = { habitIcon = it },
            label = { Text("Habit Icon") }
        )

        OutlinedTextField(
            value = habitColor,
            onValueChange = { habitColor = it },
            label = { Text("Habit Color") }
        )

        OutlinedTextField(
            value = habitTimeHour,
            onValueChange = { habitTimeHour = it },
            label = { Text("Habit Hour") }
        )

        OutlinedTextField(
            value = habitTimeMinute,
            onValueChange = { habitTimeMinute = it },
            label = { Text("Habit Minute") }
        )

        OutlinedTextField(
            value = habitDuration,
            onValueChange = { habitDuration = it },
            label = { Text("Habit Minute") }
        )

        Checkbox(
            checked = isEvent,
            onCheckedChange = { isEvent = it },
        )

        val context = LocalContext.current
        val message = "Habit Successfully Created"

        Button(
            onClick = {
                val habit = Habit(
                    name= habitName,
                    icon= habitIcon,
                    color= habitColor,
                    time= LocalTime.of(habitTimeHour.toInt(), habitTimeMinute.toInt(),0).toString(),
                    habitDuration= habitDuration.toInt(),
                    isEvent= isEvent
                )
                viewModel.insertHabit(habit)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            }
        ) {
            Text("Create Habit")
        }
    }
}
