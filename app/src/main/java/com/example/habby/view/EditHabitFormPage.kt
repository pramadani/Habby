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
import com.example.habby.viewmodel.HabitViewModel
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitFormPage(viewModel: HabitViewModel, navController: NavHostController) {
    val selectedHabit = viewModel.getSelectedHabit()

    var habitName by remember { mutableStateOf(selectedHabit.name) }
    var habitIcon by remember { mutableStateOf(selectedHabit.icon) }
    var habitColor by remember { mutableStateOf(selectedHabit.color) }
    var habitTimeHour by remember { mutableStateOf(LocalTime.parse(selectedHabit.time).hour.toString()) }
    var habitTimeMinute by remember { mutableStateOf(LocalTime.parse(selectedHabit.time).minute.toString()) }
    var habitDuration by remember { mutableStateOf(selectedHabit.habitDuration.toString()) }
    var isEvent by remember { mutableStateOf(selectedHabit.isEvent) }

    Column{
        val context = LocalContext.current
        Button(
            onClick = {
                viewModel.deleteHabit(selectedHabit)
                val deletedMessage = "Habit Deleted"
                Toast.makeText(context, deletedMessage, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            }
        ) {
            Text(text = "Delete Habit")
        }
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


        val successMessage = "Habit Successfully Updated"
        val warningMessage = "Data is Not Valid"

        Button(
            onClick = {
                if (
                    habitName.isEmpty() ||
                    habitIcon.isEmpty() ||
                    habitColor.isEmpty() ||
                    habitTimeHour.isEmpty() ||
                    habitTimeMinute.isEmpty() ||
                    habitDuration.isEmpty()
                ) {
                    Toast.makeText(context, warningMessage, Toast.LENGTH_SHORT).show()
                }
                else {
//                    val habit = Habit(
//                        name = habitName,
//                        icon = habitIcon,
//                        color = habitColor,
//                        time = LocalTime.of(habitTimeHour.toInt(), habitTimeMinute.toInt(), 0)
//                            .toString(),
//                        habitDuration = habitDuration.toInt(),
//                        isEvent = isEvent
//                    )

                    selectedHabit.name = habitName
                    selectedHabit.icon = habitIcon
                    selectedHabit.color = habitColor
                    selectedHabit.time = LocalTime.of(habitTimeHour.toInt(), habitTimeMinute.toInt(), 0).toString()
                    selectedHabit.habitDuration = habitDuration.toInt()
                    selectedHabit.isEvent = isEvent
                    viewModel.updateHabit(selectedHabit)
                    Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                    navController.navigate("Habit")
                }
            }
        ) {
            Text("Update Habit")
        }
    }
}
