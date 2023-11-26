package com.example.habby.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.habby.viewmodel.HabitViewModel

@Composable
fun CreateMenuPage(viewModel: HabitViewModel, navController: NavController) {
    Button(
        onClick = {
            navController.navigate("CreateHabitFormPage")
        }
    ) {
        Text("Create Your Own Habit")
    }
}
