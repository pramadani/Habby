package com.example.habby.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.habby.R
import com.example.habby.model.Habit
import com.example.habby.viewmodel.HabitViewModel
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMenuPage(viewModel: HabitViewModel, navController: NavController) {
//    Button(
//        onClick = {
//            navController.navigate("CreateHabitFormPage")
//        }
//    ) {
//        Text("Create Your Own Habit")
//    }

    @Composable
    fun CenteredText(text: String) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
        }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically // Align vertically centered
        ) {
            // Back button
            Button(
                onClick = {
                    navController.navigate("Habit")
                },
                modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(Color.Transparent)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
            }

            Text(
                "Create a new Habit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Card(
            onClick = {
                navController.navigate("CreateHabitFormPage")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp) // Changed height to width for Row
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(Color.Blue),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center, // Center items horizontally in the Row
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, // Replace with the desired icon
                        contentDescription = null, // Provide a content description if needed
                        tint = Color.White, // Tint color of the icon
                        modifier = Modifier.size(40.dp) // Set the size of the icon
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Create Your Own Habit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        )



        CenteredText(text = "or choice from popular habit below")

        val context = LocalContext.current

        Card(
            onClick = {
                val habit = Habit(
                    name= "Money",
                    icon= "habitIcon",
                    color= "habitColor",
                    time= LocalTime.of("1".toInt(), "1".toInt(),0).toString(),
                    habitDuration= "1".toInt(),
                    interval = "MON-TUE-WED-THU-FRI-SAT-SUN",
                    isEvent= true
                )
                val message = "Habit Money Successfully Created"

                viewModel.insertHabit(habit)
                viewModel.scheduleNotification(context)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
//            colors = CardDefaults.cardColors(Color.Gray),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.Center, // Center items horizontally in the Row
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    val imagePainter = painterResource(id = R.drawable.ic_money)
                    Icon(
                        painter = imagePainter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp)) // Add some space between icon and text

                    Column {
                        Text(
                            "Money",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Save your money for the future",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }

//                    Text(
//                        "Money\n" +
//                                "Save your money for the future",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )

                }
            }
        )


        Card(
            onClick = {
                val habit = Habit(
                    name= "Exercise",
                    icon= "habitIcon",
                    color= "habitColor",
                    time= LocalTime.of("1".toInt(), "1".toInt(),0).toString(),
                    habitDuration= "1".toInt(),
                    interval = "MON-TUE-WED-THU-FRI-SAT-SUN",
                    isEvent= true
                )
                val message = "Habit Exercise Successfully Created"

                viewModel.insertHabit(habit)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp) // Changed height to width for Column
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
//            colors = CardDefaults.cardColors(Color.Gray),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.Center, // Center items horizontally in the Row
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    val imagePainter = painterResource(id = R.drawable.ic_exercise)
                    Icon(
                        painter = imagePainter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(24.dp)) // Add some space between icon and text

                    Column {
                        Text(
                            "Exercise",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Be Healthy",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        )

        Card(
            onClick = {
                val habit = Habit(
                    name= "Sleep",
                    icon= "habitIcon",
                    color= "habitColor",
                    time= LocalTime.of("1".toInt(), "1".toInt(),0).toString(),
                    habitDuration= "1".toInt(),
                    interval = "MON-TUE-WED-THU-FRI-SAT-SUN",
                    isEvent= true
                )
                val message = "Habit Sleep Successfully Created"

                viewModel.insertHabit(habit)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp) // Changed height to width for Column
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
//            colors = CardDefaults.cardColors(Color.Gray),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.Center, // Center items horizontally in the Row
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    val imagePainter = painterResource(id = R.drawable.ic_sleep)
                    Icon(
                        painter = imagePainter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp)) // Add some space between icon and text

                    Column {
                        Text(
                            "Sleep",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Start at 23:00",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        )

        Card(
            onClick = {
                val habit = Habit(
                    name= "Morning",
                    icon= "habitIcon",
                    color= "habitColor",
                    time= LocalTime.of("1".toInt(), "1".toInt(),0).toString(),
                    habitDuration= "1".toInt(),
                    interval = "MON-TUE-WED-THU-FRI-SAT-SUN",
                    isEvent= true
                )
                val message = "Habit Morning Successfully Created"

                viewModel.insertHabit(habit)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.navigate("Habit")
            },
            modifier = Modifier
                .padding(16.dp)
                .height(80.dp) // Changed height to width for Column
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(16.dp)
                ),
//            colors = CardDefaults.cardColors(Color.Gray),
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
//                    horizontalArrangement = Arrangement.Center, // Center items horizontally in the Row
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    val imagePainter = painterResource(id = R.drawable.ic_morning)
                    Icon(
                        painter = imagePainter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp)) // Add some space between icon and text

                    Column {
                        Text(
                            "Morning",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Start at 23:00",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        )

    }

}
