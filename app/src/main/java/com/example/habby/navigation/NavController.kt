package com.example.habby.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            HabitPage(viewModel)
        }
        composable(route = "Task"){
            TaskPage()
        }
        composable(route = "Statistic"){
            StatisticPage()
        }
    }
}