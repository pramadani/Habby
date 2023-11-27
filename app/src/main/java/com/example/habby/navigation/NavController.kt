package com.example.habby.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.habby.view.CreateHabitFormPage
import com.example.habby.view.CreateMenuPage
import com.example.habby.view.EditHabitFormPage
import com.example.habby.view.HabitPage
import com.example.habby.view.StatisticPage
import com.example.habby.view.TaskPage
import com.example.habby.viewmodel.HabitViewModel

@Composable
fun NavController(navController: NavHostController, viewModel: HabitViewModel) {
    NavHost(
        navController = navController,
        startDestination = "Habit"
    ) {
        composable(route = "Habit"){
            HabitPage(viewModel, navController)
        }
        composable(route = "Task"){
            TaskPage()
        }
        composable(route = "Statistic"){
            StatisticPage()
        }
        composable(route = "CreateMenuPage"){
            CreateMenuPage(viewModel, navController)
        }
        composable(route = "CreateHabitFormPage"){
            CreateHabitFormPage(viewModel, navController)
        }
        composable(route = "EditHabitFormPage"){
            EditHabitFormPage(viewModel, navController)
        }
    }
}


