package com.example.habby.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.habby.R
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
    var habitInterval by remember { mutableStateOf("") }

    Column{
//        OutlinedTextField(
//            value = habitName,
//            onValueChange = { habitName = it },
//            label = { Text("Habit Name") }
//        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically // Align vertically centered
        ) {
            // Back button
            Button(
                onClick = {
                    navController.navigate("CreateMenuPage")
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

        LazyColumn(content = {
            item{
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(115.dp)
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(0.dp),
//                     horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally in the Row
                            verticalArrangement = Arrangement.Center // Center items vertically in the Row
                        ) {
                            Text(
                                "Habit Title",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 10.dp)
                            )
//                    Spacer(modifier = Modifier.width(8.dp))

                            OutlinedTextField(
                                value = habitName,
                                onValueChange = { habitName = it },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            )

                        }
                    }
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                ) {
                    Card(
                        onClick = {
                            habitColor = "1"
                            // navController.navigate("Habit")
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.48f) // Set width to 50% of the available width
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
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                            ) {
                                Text(
                                    "Color",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(16.dp)) // Add flexible spacer

                    Card(
                        onClick = {
                            habitIcon = "1"
                            // navController.navigate("Habit")
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth() // Take the remaining width
                            .background(
                                color = Color.Blue,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.Center, // Align to the end (right) horizontally
                                verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                            ) {
                                Text(
                                    "Icon",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    )
                }

                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(115.dp)
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(0.dp),
//                     horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally in the Row
                            verticalArrangement = Arrangement.Center // Center items vertically in the Row
                        ) {
                            Text(
                                "Habit Interval",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 10.dp)
                            )
//                    Spacer(modifier = Modifier.width(8.dp))

                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                
                                var buttonColor1 by remember { mutableStateOf(false) }
                                var buttonColor2 by remember { mutableStateOf(false) }
                                var buttonColor3 by remember { mutableStateOf(false) }
                                var buttonColor4 by remember { mutableStateOf(false) }
                                var buttonColor5 by remember { mutableStateOf(false) }
                                var buttonColor6 by remember { mutableStateOf(false) }
                                var buttonColor7 by remember { mutableStateOf(false) }

                                Card(
                                    onClick = {

                                        if (buttonColor1 == false) {
                                            habitInterval += "-SUN"
                                            buttonColor1 = true
                                        }
                                        else if (buttonColor1 == true) {
                                            habitInterval = habitInterval.replace("-SUN", "");
                                            buttonColor1 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor1 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor1 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "S",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor2 == false) {
                                            habitInterval += "-MON"
                                            buttonColor2 = true
                                        }
                                        else if (buttonColor2 == true) {
                                            habitInterval = habitInterval.replace("-MON", "");
                                            buttonColor2 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor2 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor2 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "M",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor3 == false) {
                                            habitInterval += "-TUE"
                                            buttonColor3 = true
                                        }
                                        else if (buttonColor3 == true) {
                                            habitInterval = habitInterval.replace("-TUE", "");
                                            buttonColor3 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor3 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor3 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "T",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor4 == false) {
                                            habitInterval += "-WED"
                                            buttonColor4 = true
                                        }
                                        else if (buttonColor4 == true) {
                                            habitInterval = habitInterval.replace("-WED", "");
                                            buttonColor4 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor4 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor4 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "W",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor5 == false) {
                                            habitInterval += "-THU"
                                            buttonColor5 = true
                                        }
                                        else if (buttonColor5 == true) {
                                            habitInterval = habitInterval.replace("-THU", "");
                                            buttonColor5 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor5 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor5 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "T",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor6 == false) {
                                            habitInterval += "-FRI"
                                            buttonColor6 = true
                                        }
                                        else if (buttonColor6 == true) {
                                            habitInterval = habitInterval.replace("-FRI", "");
                                            buttonColor6 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor6 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor6 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "F",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Card(
                                    onClick = {

                                        if (buttonColor7 == false) {
                                            habitInterval += "-SAT"
                                            buttonColor7 = true
                                        }
                                        else if (buttonColor7 == true) {
                                            habitInterval = habitInterval.replace("-SAT", "");
                                            buttonColor7 = false
                                        }


                                    },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .weight(1f)
                                        .background(
                                            color = if (buttonColor7 == true) Color.Blue else Color.Gray,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
                                    colors = CardDefaults.cardColors(if (buttonColor7 == true) Color.Blue else Color.Gray),
                                    content = {
                                        Row(
                                            modifier = Modifier.fillMaxSize(),
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                "S",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                    }
                                )

                            }


//                            OutlinedTextField(
//                                value = habitInterval,
//                                onValueChange = { habitInterval = it },
//                                modifier = Modifier
//                                    .padding(16.dp)
//                                    .fillMaxSize(),
//                                textStyle = TextStyle(
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 18.sp
//                                )
//                            )

                        }
                    }
                )

//        OutlinedTextField(
//            value = habitIcon,
//            onValueChange = { habitIcon = it },
//            label = { Text("Habit Icon") }
//        )
//
//        OutlinedTextField(
//            value = habitColor,
//            onValueChange = { habitColor = it },
//            label = { Text("Habit Color") }
//        )

                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(70.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    content = {
                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()
                                .padding(0.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Habit Start Time",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .width(200.dp)
                                    .fillMaxHeight()
                                    .padding(24.dp),
                            )

                            OutlinedTextField(
                                value = habitTimeHour,
                                onValueChange = { habitTimeHour = it },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Blue,
                                ),
                                modifier = Modifier
                                    .padding(0.dp)
                                    .width(60.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Blue, shape = RoundedCornerShape(16.dp)), // Set background color and rounded corners here
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center // Set text alignment to Center
                                )
                            )

                            OutlinedTextField(
                                value = habitTimeMinute,
                                onValueChange = { habitTimeMinute = it },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.Blue,
                                    unfocusedBorderColor = Color.Blue,
                                ),
                                modifier = Modifier
                                    .padding(0.dp)
                                    .width(60.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Blue, shape = RoundedCornerShape(16.dp)), // Set background color and rounded corners here
                                textStyle = TextStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center // Set text alignment to Center
                                )
                            )





                        }
                    }
                )

//        OutlinedTextField(
//            value = habitTimeHour,
//            onValueChange = { habitTimeHour = it },
//            label = { Text("Habit Hour") }
//        )
//
//        OutlinedTextField(
//            value = habitTimeMinute,
//            onValueChange = { habitTimeMinute = it },
//            label = { Text("Habit Minute") }
//        )


                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(115.dp)
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(0.dp),
//                     horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally in the Row
                            verticalArrangement = Arrangement.Center // Center items vertically in the Row
                        ) {
                            Text(
                                "Habit Duration",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 10.dp)
                            )
//                    Spacer(modifier = Modifier.width(8.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = habitDuration,
                                    onValueChange = { habitDuration = it },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxHeight()
                                        .width(250.dp),
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                                Text(text = "Minutes")
                            }

                        }
                    }
                )

//        OutlinedTextField(
//            value = habitDuration,
//            onValueChange = { habitDuration = it },
//            label = { Text("Habit Minute") }
//        )

                Card(
                    onClick = {
                        isEvent = !isEvent
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(50.dp)// Set width to 50% of the available width
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
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically // Center items vertically in the Row
                        ) {
                            Text(
                                "Set Habit time taken event",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Checkbox(
                                checked = isEvent,
                                onCheckedChange = { isEvent = it },
                            )
                        }
                    }
                )

//        Checkbox(
//            checked = isEvent,
//            onCheckedChange = { isEvent = it },
//        )

                val context = LocalContext.current
                val successMessage = "Habit Successfully Created"
//                val successMessage = habitInterval
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
                        } else {
                            val habit = Habit(
                                name = habitName,
                                icon = habitIcon,
                                color = habitColor,
                                interval = habitInterval.substring(1),
                                time = LocalTime.of(habitTimeHour.toInt(), habitTimeMinute.toInt(), 0)
                                    .toString(),
                                habitDuration = habitDuration.toInt(),
                                isEvent = isEvent
                            )
                            viewModel.insertHabit(habit)
                            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                            navController.navigate("Habit")
                        }
                    },
                    modifier = Modifier
                        .wrapContentSize()  // Make the button take the full width
                        .padding(16.dp)      // Add padding for spacing
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(Color.Green)  // Set the button color to green
                )
                {
                    Text("Create Habit")
                }
            }

        })

    }
}
