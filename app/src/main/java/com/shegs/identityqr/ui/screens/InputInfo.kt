package com.shegs.identityqr.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
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


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {

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

            Button(
                onClick = {

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
                        qrCodeImageBitmap = null, // Set this to the actual Bitmap if you have it
                        createdAt = Date()
                    )

                    val qrText = buildString {
                        append("First Name: ${firstName.text}\n")
                        append("Last Name: ${lastName.text}\n")
                        append("Email Address: ${emailAddress.text}\n")
                        append("Home Address: ${homeAddress.text}\n")
                        append("Phone Number: ${phoneNumber.text}\n")
                        append("Instagram Handle: ${instagramHandle.text}\n")
                        append("Twitter Handle: ${twitterHandle.text}\n")
                        append("Bio: ${bio.text}")
                    }
                    Log.d("QRCode", "QR Text to Generate: $qrText")
                    onGenerateQRCode(qrText)
                    viewModel.onEvent(InformationEvents.SaveUserInput(userInfo))

                    navController.navigate("qrCodeDisplay/$qrText")

                }
            ) {
                Text("Save Info")
            }
        }

    }
}