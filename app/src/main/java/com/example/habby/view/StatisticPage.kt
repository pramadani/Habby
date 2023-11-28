package com.example.habby.view



import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.habby.R
import com.example.habby.viewmodel.HabitViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun StatisticPage(viewModel: HabitViewModel, navController: NavController) {
    val habit = viewModel.habitList.collectAsState().value
    val habitEvent = viewModel.habitEventList.collectAsState().value
    viewModel.habitProgressList.collectAsState()

    Column (modifier = Modifier.fillMaxHeight()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(Color.Black)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                ) {
                    val currentDateInfo = getCurrentDateInfo()
                    val (date, month, day) = currentDateInfo
                    Text(
                        text = "$day",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
//                    modifier = Modifier
////                            .widthIn(max = 200.dp)
//                        .wrapContentSize()
//                        .padding(start = 16.dp) // Add left padding
////                                    .align(Alignment.CenterVertically)
                    )
                    Text("$month $date")
                }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {


            //Kolom Bawah
            Column(
                modifier = Modifier
//                    .wrapContentHeight()
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(color = Color(0xFF1A1B20), RoundedCornerShape(12.dp, 12.dp))
            ) {
                //Kolom ALl Statistics
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color(0xFF363C4A), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                )
                {
                    Text(
                        text = "All Statistics",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight(800)
                        ),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(8.dp)
                    )
                    //Row 1
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp
                            )
                            .width(800.dp)
                            .height(50.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    end = 8.dp
                                )
                                .background(
                                    Color(0xFF397CFF),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .width(100.dp)
                                .fillMaxHeight(),
                        ) {
                            val percentage = viewModel.getStatisticAllPercentage()
                            Text(
                                text = "$percentage %",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight(800),
                                ),
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(8.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    end = 0.dp
                                )
                                .background(
                                    Color.Transparent,
                                    RoundedCornerShape(10.dp)
                                )
                                .width(310.dp)
                                .fillMaxHeight()
                                .border(
                                    5.dp,
                                    Color(0xFF397CFF),
                                    RoundedCornerShape(10.dp)
                                )
                        ) {
                            val allProgress = viewModel.getStatisticAllEveryProgress()
                            val trueProgress = viewModel.getStatisticAllTrueProgress()
                            Text(
                                text = "$trueProgress / $allProgress",
                                fontSize = 24.sp,
                                color = Color.White,
                                style = TextStyle(
                                    fontWeight = FontWeight(800)
                                ),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    //Row 2
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 5.dp,
                                end = 10.dp,
                                bottom = 5.dp
                            )
                            .width(800.dp)
                            .height(65.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Streak",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight(300)
                                ),
                                modifier = Modifier.padding(start = 8.dp, top = 0.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(8.dp)
                            ) {
                                val currentStreak = viewModel.getStatisticCurrentAllStreak()
                                Text(
                                    text = currentStreak.toString(),
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(800)
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Best Streak",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight(300)
                                ),
                                modifier = Modifier.padding(start = 8.dp, top = 0.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(8.dp)
                            ) {
                                val allStreak = viewModel.getStatisticAllStreak()
                                Text(
                                    text = allStreak.toString(),
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(800)
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }

                    //Row 3
                    Row(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                top = 5.dp,
                                end = 10.dp,
                                bottom = 5.dp
                            )
                            .width(800.dp)
                            .height(65.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Average Duration",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight(300)
                                ),
                                modifier = Modifier.padding(start = 8.dp, top = 0.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(8.dp)
                            ) {
                                    val averageDur = viewModel.getStatisticAllAverageDuration()
                                Text(
                                    text = "$averageDur Hours",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(800)
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Total Duration",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight(300)
                                ),
                                modifier = Modifier.padding(start = 8.dp, top = 0.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .padding(8.dp)
                            ) {
                                val totalDuration = viewModel.getStatisticAllTotalDuration()
                                Text(
                                    text = "$totalDuration Hours",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(800)
                                    ),
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }
            }
        }
//        LazyColumn(
//            modifier = Modifier.fillMaxHeight(),
//            content = {
//                item {
//
//
//                            //Kolom Habit Statistics
////                            Column(modifier = Modifier
////                                .padding(16.dp)
////                                .fillMaxWidth()
////                                .wrapContentHeight()
////                                .background(Color(0xFF363C4A))
////                                .padding(16.dp)
////                            )
////                            {
////                                Text(text = "Habit Statistics",
////                                    style = TextStyle(
////                                        fontSize = 20.sp,
////                                        fontWeight = FontWeight(800)
////                                    ),
////                                    color = Color.White,
////                                    modifier = Modifier
////                                        .align(Alignment.Start)
////                                        .padding(8.dp)
////                                )
////
////                                //Row 1
////                                Row(modifier = Modifier
////                                    .padding(
////                                        start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp
////                                    )
////                                    .width(800.dp)
////                                    .height(50.dp)
////                                ){
////                                    Box(
////                                        modifier = Modifier
////                                            .padding(
////                                                start = 0.dp,
////                                                top = 0.dp,
////                                                bottom = 0.dp,
////                                                end = 8.dp
////                                            )
////                                            .background(
////                                                Color(0xFF397CFF),
////                                                shape = RoundedCornerShape(10.dp)
////                                            )
////                                            .fillMaxHeight()
////                                            .fillMaxWidth(),
////                                    ) {
////                                        Row(modifier = Modifier
////                                            .fillMaxSize()
////                                            .padding(0.dp),
////                                            verticalAlignment = Alignment.CenterVertically
////                                        )
////                                        {
////                                            val imagePainter = painterResource(id = R.drawable.ic_launcher_foreground)
////                                            Icon(
////                                                painter = imagePainter,
////                                                contentDescription = null,
////                                                tint = Color.White,
////                                                modifier = Modifier.size(40.dp)
////                                            )
////
////                                            Text(text = "Sleep",
////                                                style = TextStyle(
////                                                    fontSize = 24.sp,
////                                                    fontWeight = FontWeight(800),
////                                                ),
////                                                color = Color.White,
////                                                modifier = Modifier
////                                                    .padding(8.dp))
////                                        }
////
////                                    }
////                                }
////                                //Row 2
////                                Row(modifier = Modifier
////                                    .padding(
////                                        start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp
////                                    )
////                                    .width(800.dp)
////                                    .height(50.dp),
////                                    verticalAlignment = Alignment.CenterVertically,
////                                    horizontalArrangement = Arrangement.Center
////                                ){
////                                    Box(
////                                        modifier = Modifier
////                                            .padding(
////                                                start = 0.dp,
////                                                top = 0.dp,
////                                                bottom = 0.dp,
////                                                end = 8.dp
////                                            )
////                                            .background(
////                                                Color(0xFF397CFF),
////                                                shape = RoundedCornerShape(10.dp)
////                                            )
////                                            .width(100.dp)
////                                            .fillMaxHeight(),
////                                    ) {
////                                        Text(text = "82.0%",
////                                            style = TextStyle(
////                                                fontSize = 24.sp,
////                                                fontWeight = FontWeight(800),
////                                            ),
////                                            color = Color.White,
////                                            modifier = Modifier
////                                                .align(Alignment.Center)
////                                                .padding(8.dp))
////                                    }
////
////                                    Box(
////                                        modifier = Modifier
////                                            .padding(
////                                                start = 8.dp,
////                                                top = 0.dp,
////                                                bottom = 0.dp,
////                                                end = 0.dp
////                                            )
////                                            .background(
////                                                Color.Transparent,
////                                                RoundedCornerShape(10.dp)
////                                            )
////                                            .width(310.dp)
////                                            .fillMaxHeight()
////                                            .border(
////                                                5.dp,
////                                                Color(0xFF397CFF),
////                                                RoundedCornerShape(10.dp)
////                                            )
////                                    ) {
////                                        Text(text = "164/200",
////                                            fontSize = 24.sp,
////                                            color = Color.White,
////                                            style = TextStyle(
////                                                fontWeight = FontWeight(800)
////                                            ),
////                                            modifier = Modifier.align(Alignment.Center))
////                                    }
////                                }
////
////                                //Row 2
////                                Row(
////                                    modifier = Modifier
////                                        .padding(
////                                            start = 10.dp,
////                                            top = 5.dp,
////                                            end = 10.dp,
////                                            bottom = 5.dp
////                                        )
////                                        .width(800.dp)
////                                        .height(65.dp)
////                                ) {
////                                    Column(
////                                        modifier = Modifier
////                                            .weight(1f)
////                                            .padding(end = 10.dp)
////                                            .fillMaxHeight()
////                                    ) {
////                                        Text(
////                                            text = "Streak",
////                                            style = TextStyle(
////                                                fontSize = 16.sp,
////                                                color = Color.White,
////                                                fontWeight = FontWeight(300)
////                                            ),
////                                            modifier = Modifier.padding(start = 8.dp, top = 0.dp)
////                                        )
////
////                                        Box(
////                                            modifier = Modifier
////                                                .weight(4f)
////                                                .padding(8.dp)
////                                        ) {
////                                            Text(
////                                                text = "64",
////                                                style = TextStyle(
////                                                    fontSize = 24.sp,
////                                                    fontWeight = FontWeight(800)
////                                                ),
////                                                color = Color.White,
////                                                modifier = Modifier.align(Alignment.CenterStart)
////                                            )
////                                        }
////                                    }
////                                    Column(
////                                        modifier = Modifier
////                                            .weight(1f)
////                                            .padding(start = 10.dp)
////                                            .fillMaxHeight()
////                                    ) {
////                                        Text(
////                                            text = "Best Streak",
////                                            style = TextStyle(
////                                                fontSize = 16.sp,
////                                                color = Color.White,
////                                                fontWeight = FontWeight(300)
////                                            ),
////                                            modifier = Modifier.padding(start = 8.dp, top = 0.dp)
////                                        )
////
////                                        Box(
////                                            modifier = Modifier
////                                                .weight(4f)
////                                                .padding(8.dp)
////                                        ) {
////                                            Text(
////                                                text = "77",
////                                                style = TextStyle(
////                                                    fontSize = 24.sp,
////                                                    fontWeight = FontWeight(800)
////                                                ),
////                                                color = Color.White,
////                                                modifier = Modifier.align(Alignment.CenterStart)
////                                            )
////                                        }
////                                    }
////                                }
////
////                                //Row 3
////                                Row(
////                                    modifier = Modifier
////                                        .padding(
////                                            start = 10.dp,
////                                            top = 5.dp,
////                                            end = 10.dp,
////                                            bottom = 5.dp
////                                        )
////                                        .width(800.dp)
////                                        .height(65.dp)
////                                ) {
////                                    Column(
////                                        modifier = Modifier
////                                            .weight(1f)
////                                            .padding(end = 10.dp)
////                                            .fillMaxHeight()
////                                    ) {
////                                        Text(
////                                            text = "Average Duration",
////                                            style = TextStyle(
////                                                fontSize = 16.sp,
////                                                color = Color.White,
////                                                fontWeight = FontWeight(300)
////                                            ),
////                                            modifier = Modifier.padding(start = 8.dp, top = 0.dp)
////                                        )
////
////                                        Box(
////                                            modifier = Modifier
////                                                .weight(4f)
////                                                .padding(8.dp)
////                                        ) {
////                                            val averageDuration = viewModel.getStatisticAllAverageDuration()
////                                            Text(
////                                                text = "$averageDuration min",
////                                                style = TextStyle(
////                                                    fontSize = 24.sp,
////                                                    fontWeight = FontWeight(800)
////                                                ),
////                                                color = Color.White,
////                                                modifier = Modifier.align(Alignment.CenterStart)
////                                            )
////                                        }
////                                    }
////                                    Column(
////                                        modifier = Modifier
////                                            .weight(1f)
////                                            .padding(start = 10.dp)
////                                            .fillMaxHeight()
////                                    ) {
////                                        Text(
////                                            text = "Total Duration",
////                                            style = TextStyle(
////                                                fontSize = 16.sp,
////                                                color = Color.White,
////                                                fontWeight = FontWeight(300)
////                                            ),
////                                            modifier = Modifier.padding(start = 8.dp, top = 0.dp)
////                                        )
////
////                                        Box(
////                                            modifier = Modifier
////                                                .weight(4f)
////                                                .padding(8.dp)
////                                        ) {
////                                            Text(
////                                                text = "10.5 Hours",
////                                                style = TextStyle(
////                                                    fontSize = 24.sp,
////                                                    fontWeight = FontWeight(800)
////                                                ),
////                                                color = Color.White,
////                                                modifier = Modifier.align(Alignment.CenterStart)
////                                            )
////                                        }
////                                    }
////                                }
////
////                            }
//                        }
//                    }
                }
            }


//        )
//    }

//}