package com.shegs.identityqr.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: InformationViewModel, navController: NavHostController) {
    val cards = viewModel.getAllInformation.collectAsState(initial = emptyList()).value

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(cards) { card ->

                    Row(modifier = Modifier) {
                        Text(
                            text = card.createdAt?.format(dateFormatter) ?: "",
                            fontSize = 12.sp
                        )
                        
                        Spacer(modifier = Modifier.weight(1f))

                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(140.dp)
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
                            onClick = {
                                // Navigate to the "displayInfo" screen with the selected card's infoId
                                navController.navigate("displayInfo/${card.infoId}")
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "${card.cardName}",
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = card.createdAt?.format(timeFormatter) ?: "",
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = {
                                            viewModel.viewModelScope.launch {
                                                viewModel.onEvent(
                                                    InformationEvents.DeleteInformation(
                                                        card
                                                    )
                                                )
                                            }
                                        }

                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete Card"
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
}
