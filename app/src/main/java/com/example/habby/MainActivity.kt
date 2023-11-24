package com.example.habby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.habby.data.Habit
import com.example.habby.data.HabitViewModel
import com.example.habby.ui.theme.HabbyTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabbyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HabitScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitScreen(viewModel: HabitViewModel = viewModel()) {
    val habits by viewModel.allHabits.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Habit Tracker") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.insertHabit(
                                Habit(name = "New Habit", description = "", frequency = 0)
                            )
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit")
                    }
                }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        HabitList(
            habits = habits,
            modifier = modifier,
            onEditClick = { habitId -> /* Handle edit click */ },
            onDeleteClick = { habitId -> /* Handle delete click */ }
        )
    }
}

@Composable
fun HabitList(
    habits: List<Habit>,
    modifier: Modifier = Modifier,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(habits) { habit ->
            HabitItem(
                habit = habit,
                onEditClick = { onEditClick(habit.id) },
                onDeleteClick = { onDeleteClick(habit.id) }
            )
        }
    }
}

@Composable
fun HabitItem(habit: Habit, onEditClick: () -> Unit, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = habit.name)
            Text(text = habit.description)
        }
        IconButton(onClick = onEditClick) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Habit")
        }
        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Habit")
        }
    }
}

