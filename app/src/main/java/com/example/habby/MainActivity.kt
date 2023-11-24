package com.example.habby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.habby.data.Habit
import com.example.habby.data.HabitDatabase
import com.example.habby.data.HabitViewModel
import com.example.habby.ui.theme.HabbyTheme


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            HabitDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<HabitViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HabitViewModel(db.habitDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabbyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HabitScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HabitScreen(viewModel: HabitViewModel) {
    val habits = viewModel.habitList.collectAsState().value

    Column {
        IconButton(
            onClick = {
                viewModel.insertHabit(
                    Habit(name = "New Habit", description = "", frequency = 0)
                )
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit")
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
                habit = habit,
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