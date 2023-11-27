package com.example.habby.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habby.R
import com.example.habby.model.Habit
import com.example.habby.model.HabitEvent
import com.example.habby.viewmodel.HabitViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getCurrentDateInfo(): Triple<Int, String, String> {
    val calendar = Calendar.getInstance()

    // Mendapatkan hari
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    // Mendapatkan bulan (dimulai dari 0 untuk Januari)
    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)

    // Mendapatkan tahun
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

    return Triple(dayOfMonth, monthName, dayOfWeek)
}

@Composable
fun HabitPage(viewModel: HabitViewModel, navController: NavController) {
    val habits = viewModel.habitList.collectAsState().value

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
            ) {
                val currentDateInfo = getCurrentDateInfo()
                val (date, month, day) = currentDateInfo
                Text("$day")
                Text("$month $date")
            }
            Button(
                onClick = {
                    navController.navigate("CreateMenuPage")
                },
                modifier = Modifier
//                    .align(Alignment.End)
                    .padding(16.dp)
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    "Add Habit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
        HabitList(habits, viewModel, navController)
    }

}
@Composable
fun HabitList(habits: List<Habit>, viewModel: HabitViewModel, navController: NavController) {

    viewModel.habitEventList.collectAsState().value

    LazyColumn {
        items(habits) { habit ->
            val habitEvent = viewModel.getHabitEventByHabitId(habit.habitId)
            Column {
                HabitItem(habit, viewModel, habitEvent, navController)
                Spacer(modifier = Modifier.height(0.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitItem(habit: Habit, viewModel: HabitViewModel, habitEvent: HabitEvent?, navController: NavController) {
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {

        Card( onClick = {
            viewModel.setSelectedHabit(habit)
            navController.navigate("EditHabitFormPage")
        },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp),
                ),
            colors = CardDefaults.cardColors(Color.Blue),
            content = {

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    var isEventEndTime = if (habitEvent?.eventEndTime == null) false else true
                    var isChecked by remember { mutableStateOf(habit.isCheck) }
                    var isStart by remember { mutableStateOf(false) }
                    var isStopped by remember { mutableStateOf(isEventEndTime) }

                    Text(
                        habit.icon,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .width(50.dp) // Set the width to 50 dp
                            .align(Alignment.CenterVertically) // Align the habit icon to the center vertically
                    )

                    // Text for habit.name (aligned to the left)
                    Column {
                        Text(
                            habit.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
//                            .widthIn(max = 200.dp)
                                .wrapContentSize()
                                .padding(start = 16.dp) // Add left padding
//                                .align(Alignment.CenterVertically)
                        )

                        if (isStopped == true) {
                            Text(
                                text = "End at ${habitEvent?.eventEndTime}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
//                            .widthIn(max = 200.dp)
                                    .wrapContentSize()
                                    .padding(start = 16.dp) // Add left padding
//                                    .align(Alignment.CenterVertically)
                            )

                        }
                        else if (isStart == true) {

                            Text(
                                text = "Start at ${habitEvent?.eventStartTime}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
//                            .widthIn(max = 200.dp)
                                    .wrapContentSize()
                                    .padding(start = 16.dp) // Add left padding
//                                    .align(Alignment.CenterVertically)
                            )
                        }

                        else if (habit.isEvent == true) {
                            Text(
                                text = "${habit.habitDuration} minutes",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
//                            .widthIn(max = 200.dp)
                                    .wrapContentSize()
                                    .padding(start = 16.dp) // Add left padding
//                                    .align(Alignment.CenterVertically)
                            )
                        }

                    }

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )

                    // RadioButton or Button based on habit.isEvent
                    if (isStopped == true) {
                        // RadioButton

                    }

                    else if (!habit.isEvent) {
                        RadioButton(
                            selected = isChecked,
                            onClick = {
                                isChecked = !isChecked
                                if (isChecked) {
                                    viewModel.checkHabit(habit)
                                } else {
                                    viewModel.unCheckHabit(habit)
                                }
                            },
                            modifier = Modifier
                                .padding(end = 12.dp)

//                                .width(30.dp)
                                .wrapContentSize(),
                        )
                    }

                    else if (!isStart) {


                        Button(
                            onClick = {
                                isChecked = true
                                viewModel.startHabitEvent(habit)
                                isStart = true
                            },
                            modifier = Modifier
                                .padding(end = 0.dp)
                                .wrapContentSize(),
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            val imagePainterStart = painterResource(id = R.drawable.ic_start_event)
                            Icon(
                                painter = imagePainterStart,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    else {

                        Button(
                            onClick = {
                                isChecked = true
                                isStopped = true
                                viewModel.stopHabitEvent(habit)
                                isStart = false
                            },
                            modifier = Modifier
                                .padding(end = 0.dp)
                                .wrapContentSize(),
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            val imagePainterStop = painterResource(id = R.drawable.ic_stop_circle)

                            Icon(
                                painter = imagePainterStop,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp) // Adjust the size here
                            )
                        }

                    }
                }

            }
        )
    }
}