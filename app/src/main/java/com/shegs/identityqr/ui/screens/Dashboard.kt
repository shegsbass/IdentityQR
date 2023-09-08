package com.shegs.identityqr.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.shegs.identityqr.R
import com.shegs.identityqr.navigation.bottomnav.NavItem
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(viewModel: InformationViewModel, navController: NavHostController) {
    var selectedInfoId by remember { mutableStateOf<Int?>(null) }

    val cards = viewModel.getAllInformation.collectAsState(initial = emptyList()).value

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    // Define a constant for the dismiss threshold
    val DISMISS_THRESHOLD = 100.dp

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { DisplayInfoScreen(infoId = selectedInfoId ?: 0, viewModel = viewModel) },
        sheetShape = RoundedCornerShape(24.dp),
        sheetGesturesEnabled = true,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F3FA))
        ) {
            pageTitleSection()
            Spacer(modifier = Modifier.height(16.dp))
            topSection(navController)
            historySection()

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, bottom = 16.dp, end = 8.dp),
            ) {
                items(cards) { card ->

                    val dismissState = rememberDismissState()

                    // Use LaunchedEffect to handle the side effect when card is dismissed
                    LaunchedEffect(dismissState.isDismissed(DismissDirection.EndToStart)) {
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            // Delete the card from the database
                            viewModel.onEvent(InformationEvents.DeleteInformation(card))
                        }
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { direction ->
                            // Set the threshold for dismissing the card
                            FixedThreshold(DISMISS_THRESHOLD)
                        },
                        background = {
                            Spacer(modifier = Modifier.width(120.dp))
                            Row(
                                modifier = Modifier
                                    .width(280.dp)
                                    .height(100.dp)
                                    .padding(8.dp)
                                    .background(Color.Red, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete card"
                                    )
                                }


                            }
                        },

                        dismissContent = {
                            Row(
                                modifier = Modifier
                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(top = 12.dp)
                                ) {
                                    Text(
                                        text = "Created on",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                        fontWeight = FontWeight(600),
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )

                                    Text(
                                        text = card.createdAt?.format(dateFormatter) ?: "",
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                        fontWeight = FontWeight(400),
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Card(
                                    modifier = Modifier
                                        .width(280.dp)
                                        .height(100.dp)
                                        .padding(8.dp),
                                    elevation = CardDefaults.cardElevation(1.dp),
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
                                    onClick = {
                                        coroutineScope.launch {
                                            selectedInfoId = card.infoId
                                            if (sheetState.isVisible) sheetState.hide()
                                            else sheetState.show()
                                        }
                                    }
//                                    onClick = {
//                                        // Navigate to the "displayInfo" screen with the selected card's infoId
//                                        navController.navigate("displayInfo/${card.infoId}")
//                                    }
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .padding(12.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "${card.cardName}",
                                                fontSize = 18.sp,
                                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                                fontWeight = FontWeight(600),
                                                letterSpacing = 1.sp
                                            )
                                            Text(
                                                text = card.createdAt?.format(timeFormatter) ?: "",
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                                fontWeight = FontWeight(400)
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(
                                                    modifier = Modifier
                                                        .padding(12.dp),
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = "${card.cardName}",
                                                        fontSize = 18.sp,
                                                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                                        fontWeight = FontWeight(600),
                                                        letterSpacing = 1.sp
                                                    )
                                                    Text(
                                                        text = card.createdAt?.format(timeFormatter)
                                                            ?: "",
                                                        fontSize = 12.sp,
                                                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                                        fontWeight = FontWeight(400)
                                                    )

                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.End,
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
                                                            contentDescription = "Delete Card",
                                                            modifier = Modifier.size(16.dp)
                                                        )
                                                    }
                                                }
                                            }

                                        }

                                    }

                                }

                            }


                        }

                    )
                }

            }
            Spacer(modifier = Modifier.height(100.dp))

        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topSection(navController: NavHostController) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .width(180.dp)
                .height(260.dp)
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
            onClick = {
                navController.navigate(NavItem.Create.screenRoute)
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle_outline),
                    contentDescription = "Add card",
                    tint = MaterialTheme.colorScheme.tertiaryContainer,
                    modifier = Modifier
                        .size(80.dp)
                )

                Text(
                    text = "Create Card",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onTertiary
                )

                Text(
                    text = "Your journey to digital identity begins here. Let's get started",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    lineHeight = 14.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }

        Card(
            modifier = Modifier
                .width(180.dp)
                .height(260.dp)
                .padding(start = 8.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.scrim),
            onClick = {
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.qr_code_scanner),
                    contentDescription = "Add card",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .size(80.dp)
                        .alpha(0.5F)
                )

                Text(
                    text = "Scan QR",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_black)),
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

        }
    }
}

@Composable
fun historySection() {
    Column(
        modifier = Modifier
            .padding(top = 28.dp, start = 16.dp, end = 16.dp)
    )
    {
        Text(
            text = "Card History",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(600),
            color = Color.Black
        )
    }
}

@Composable
fun pageTitleSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "IdentityQR",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.roboto_bold)),
            fontWeight = FontWeight(600),
            color = Color.Black
        )
    }
}
