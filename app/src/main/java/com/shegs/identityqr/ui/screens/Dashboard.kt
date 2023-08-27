package com.shegs.identityqr.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: InformationViewModel, navController: NavController) {
    val cardState = viewModel.infoState.collectAsState().value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.viewModelScope.launch {
                    navController.navigate("addInfo")
                }

            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Card"
                )
            }
        },

        content = {_ ->
            cardState.isAddingInformation
            val cards = viewModel.getAllInformation.collectAsState(initial = emptyList()).value

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cards) { card ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
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
                                text = "${card.createdAt}",
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(
                                    onClick = { viewModel.viewModelScope.launch {
                                        viewModel.onEvent(InformationEvents.DeleteInformation(card)) }
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
    )
}