package com.shegs.identityqr.ui.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shegs.identityqr.navigation.topnav.TopNavigation
import com.shegs.identityqr.ui.viewmodel.InformationViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun DisplayInfoScreen(infoId: Int, viewModel: InformationViewModel, navController: NavController) {

    viewModel.getSelectedInformation(infoId)

    val card by viewModel.selectedInformation.collectAsState()

    TopNavigation(navController, "${card?.cardName}" + " QR Details" )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3FA))
            .padding(16.dp, top = 70.dp, bottom = 62.dp)
    ) {
        item {

            card?.let { card ->

                    Text(
                        text = "First Name: ${card.firstName}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Last Name: ${card.lastName}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Email Address: ${card.emailAddress}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Home Address: ${card.homeAddress}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Phone Number: ${card.phoneNumber}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "InstagramHandle: ${card.instagramHandle}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "TwitterHandle: ${card.twitterHandle}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Bio: ${card.bio}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Display the QR code image if it's available in the card
                    card.qrCodeImage?.let { qrCodeImageByteArray ->
                        val imageBitmap = BitmapFactory.decodeByteArray(qrCodeImageByteArray, 0, qrCodeImageByteArray.size)?.asImageBitmap()
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(200.dp) // Adjust the size as needed
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
}