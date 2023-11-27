package com.example.habby.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.habby.R
import com.example.habby.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavLayout(viewModel: HabitViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                var selectedItem by remember { mutableIntStateOf(0) }
                val items = listOf("HABIT", "STATISTIC")
                var habit = painterResource(id = R.drawable.ic_habit)
                var statistic = painterResource(id = R.drawable.ic_statistic)

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = if (item.equals("HABIT")) habit else statistic,
                                contentDescription = item,
                                modifier = Modifier.size(30.dp) // Sesuaikan ukuran ikon sesuai kebutuhan
                            )
                        },
                        label = {
                            Text(
                                text = item,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
//                    modifier = Modifier
////                            .widthIn(max = 200.dp)
//                        .wrapContentSize()
//                        .padding(start = 16.dp) // Add left padding
////                                    .align(Alignment.CenterVertically)
                            )
                                },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item)
                        }
                    )

                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            NavController(navController = navController, viewModel)

        }
    }
}