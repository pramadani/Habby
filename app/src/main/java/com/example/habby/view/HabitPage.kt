package com.example.habby.view

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.PrimaryKey
import com.example.habby.model.Habit
import com.example.habby.notification.Notification
import com.example.habby.viewmodel.HabitViewModel
import java.time.LocalTime
import java.util.UUID

@Composable
fun HabitPage(viewModel: HabitViewModel) {
    val habits = viewModel.habitList.collectAsState().value

    Column(
        modifier = Modifier
            .width(1080.dp)
            .height(2340.dp)
            .background(color = Color(0xFF121317)),
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Button(
            onClick = {
                viewModel.insertHabit(
                    Habit(
                        name= "String",
                        icon= "String",
                        color= "String",
                        time= LocalTime.of(1,1,1).toString(),
                        habitDuration= 30,
                    )
                )
            }
        ) {
            Text("Add Habit")
        }
        val service = Notification(LocalContext.current)

        Button(onClick = { service.showNotification() }) {
            Text(text = "Show Notification")}

        Column(
            modifier = Modifier
                .width(1080.dp)
                .height(1837.dp)
                .padding(0.dp)
                .background(
                    color = Color(0xFF08184c), shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
            ,
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start)
        {
            HabitList(
                habits = habits
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

    }

}
@Composable
fun HabitList(
    habits: List<Habit>,
) {
    LazyColumn {
        items(habits) { habit ->
            Row(modifier = Modifier
                .padding(10.dp)
                .width(500.dp)
                .height(100.dp)
                .background(color = Color(0xFF397CFF), shape = RoundedCornerShape(size = 50.dp))
                .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            )
            {
                HabitItem(
                    habit = habit
                )
            }

        }
    }
}

@Composable
fun HabitItem(habit: Habit) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = habit.habitId)
            Text(text = habit.name)
        }
    }
}