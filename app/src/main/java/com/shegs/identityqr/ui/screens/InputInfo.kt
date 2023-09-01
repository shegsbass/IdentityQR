package com.shegs.identityqr.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shegs.identityqr.navigation.topnav.TopNavigation
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import com.shegs.identityqr.util.generateQRCode
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputInfoScreen(
    viewModel: InformationViewModel,
    onGenerateQRCode: (String) -> Unit,
    navController: NavHostController
) {
// Remember the values of the input fields
    var firstName by remember { mutableStateOf(TextFieldValue()) }
    var lastName by remember { mutableStateOf(TextFieldValue()) }
    var cardName by remember { mutableStateOf(TextFieldValue()) }
    var emailAddress by remember { mutableStateOf(TextFieldValue()) }
    var homeAddress by remember { mutableStateOf(TextFieldValue()) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue()) }
    var instagramHandle by remember { mutableStateOf(TextFieldValue()) }
    var twitterHandle by remember { mutableStateOf(TextFieldValue()) }
    var bio by remember { mutableStateOf(TextFieldValue()) }

    TopNavigation(navController, "Card Information")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3FA))
            .padding(start = 16.dp, end = 16.dp, top = 70.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {

            Column {
                TextField(
                    value = cardName.text,
                    onValueChange = { cardName = cardName.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Enter Card Name") }
                )

                TextField(
                    value = firstName.text,
                    onValueChange = { firstName = firstName.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("First Name") }
                )

                TextField(
                    value = lastName.text,
                    onValueChange = { lastName = lastName.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Last Name") }
                )

                TextField(
                    value = emailAddress.text,
                    onValueChange = { emailAddress = emailAddress.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Email") }
                )

                TextField(
                    value = homeAddress.text,
                    onValueChange = { homeAddress = homeAddress.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Home Address") }
                )


                TextField(
                    value = phoneNumber.text,
                    onValueChange = { phoneNumber = phoneNumber.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Phone Number") }
                )

                TextField(
                    value = instagramHandle.text,
                    onValueChange = { instagramHandle = instagramHandle.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Instagram Handle") }
                )

                TextField(
                    value = twitterHandle.text,
                    onValueChange = { twitterHandle = twitterHandle.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Twitter Handle") }
                )

                TextField(
                    value = bio.text,
                    onValueChange = { bio = bio.copy(text = it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Bio") }
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, bottom = 62.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val qrText = buildString {
                                    append("First Name: $firstName\n")
                                    append("Last Name: $lastName\n")
                                    append("Email Address: $emailAddress\n")
                                    append("Home Address: $homeAddress\n")
                                    append("Phone Number: $phoneNumber\n")
                                    append("Instagram Handle: $instagramHandle\n")
                                    append("Twitter Handle: $twitterHandle\n")
                                    append("Bio: $bio")
                                }

                                val qrBitmap =
                                    generateQRCode(qrText, 300) // Generate QR code as Bitmap
                                val userInfo = InformationEvents.UserInput(
                                    cardName = cardName.text,
                                    firstName = firstName.text,
                                    lastName = lastName.text,
                                    emailAddress = emailAddress.text,
                                    homeAddress = homeAddress.text,
                                    phoneNumber = phoneNumber.text,
                                    instagramHandle = instagramHandle.text,
                                    twitterHandle = twitterHandle.text,
                                    bio = bio.text,
                                    qrCodeImageBitmap = qrBitmap, // Pass the QR code Bitmap
                                    createdAt = Date()
                                )

                                onGenerateQRCode(qrText)
                                viewModel.onEvent(InformationEvents.SaveUserInput(userInfo))

                                navController.navigate("qrCodeDisplay/$qrText")
                            }
                    ) {
                        Text(
                            text = "Save Info",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }

        }

    }
}