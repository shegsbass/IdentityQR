package com.shegs.identityqr.ui.screens

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shegs.identityqr.R
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

    Box(modifier = Modifier.fillMaxSize()) {


        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
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
        IdentityCard(firstName =if(firstName.text.isEmpty()) {
            null
        } else firstName.text , lastName = if(lastName.text.isEmpty()) {
            null
        } else lastName.text)


        val customColor = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            disabledLabelColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = if (isLandscape) 70.dp else 320.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {

                Column() {
                    val maxLength = 20
                    val minLength = 3

                    Text(
                        text = "Card Name",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp, top = 24.dp),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp
                    )

                    TextField(
                        value = cardName.text,
                        onValueChange = { cardName = cardName.copy(text = it) },
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = "Enter Card Name",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily(Font(R.font.roboto_light)),
                                fontWeight = FontWeight(300),
                                fontSize = 12.sp
                            ) },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        colors = customColor,
                        trailingIcon = {
                            IconButton(onClick = { cardName = TextFieldValue() }) {
                                if (cardName.text.length in minLength..maxLength) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        tint = Color(0xFF006400),
                                        contentDescription = null
                                    )
                                }else{
                                    Icon(
                                        imageVector = Icons.Outlined.Clear,
                                        tint = Color.Red,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    )
                    Text(
                        text = "${cardName.text.length} / $maxLength",
                        fontSize = 8.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )


                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        ) {
                            Text(
                                text = "First Name",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(600),
                                fontSize = 12.sp
                            )
                            TextField(
                                value = firstName.text,
                                onValueChange = { firstName = firstName.copy(text = it) },
                                singleLine = true,
                                placeholder = {
                                    Text(
                                        text = "First Name",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontFamily = FontFamily(Font(R.font.roboto_light)),
                                        fontWeight = FontWeight(300),
                                        fontSize = 12.sp
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                colors = customColor,
                                trailingIcon = {
                                    if (firstName.text.isNotEmpty() && firstName.text.length >= minLength) {
                                        IconButton(onClick = { firstName = TextFieldValue() }) {
                                            Icon(
                                                imageVector = Icons.Outlined.Check,
                                                tint = Color(0xFF006400),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Text(
                                text = "Last Name",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 4.dp),
                                textAlign = TextAlign.Start,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(600),
                                fontSize = 12.sp
                            )

                            TextField(
                                value = lastName.text,
                                onValueChange = { lastName = lastName.copy(text = it) },
                                singleLine = true,
                                placeholder = {
                                    Text(
                                        text = "Last Name",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontFamily = FontFamily(Font(R.font.roboto_light)),
                                        fontWeight = FontWeight(300),
                                        fontSize = 12.sp
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                colors = customColor,
                                trailingIcon = {
                                    if (lastName.text.isNotEmpty() && lastName.text.length >= minLength) {
                                        IconButton(onClick = { lastName = TextFieldValue() }) {
                                            Icon(
                                                imageVector = Icons.Outlined.Check,
                                                tint = Color(0xFF006400),
                                                contentDescription = null
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                            )
                        }
                    }


                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Email Address",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = emailAddress.text,
                            onValueChange = { emailAddress = emailAddress.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Email",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = customColor,
                            trailingIcon = {
                                if (emailAddress.text.isNotEmpty() && emailAddress.text.length >= minLength) {
                                    IconButton(onClick = { emailAddress = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    }


                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Home Address",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = homeAddress.text,
                            onValueChange = { homeAddress = homeAddress.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Home Address",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = customColor,
                            trailingIcon = {
                                if (homeAddress.text.isNotEmpty() && homeAddress.text.length >= minLength) {
                                    IconButton(onClick = { homeAddress = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    }


                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Phone Number",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = phoneNumber.text,
                            onValueChange = { phoneNumber = phoneNumber.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Phone Number",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = customColor,
                            trailingIcon = {
                                if (phoneNumber.text.isNotEmpty() && phoneNumber.text.length >= minLength) {
                                    IconButton(onClick = { phoneNumber = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Instagram Handle",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = instagramHandle.text,
                            onValueChange = { instagramHandle = instagramHandle.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Instagram Handle",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = customColor,
                            trailingIcon = {
                                if (instagramHandle.text.isNotEmpty() && instagramHandle.text.length >= minLength) {
                                    IconButton(onClick = { instagramHandle = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    }


                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Twitter Handle",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = twitterHandle.text,
                            onValueChange = { twitterHandle = twitterHandle.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Twitter Handle",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            colors = customColor,
                            trailingIcon = {
                                if (twitterHandle.text.isNotEmpty() && twitterHandle.text.length >= minLength) {
                                    IconButton(onClick = { twitterHandle = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(end = 4.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Bio",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontFamily = FontFamily(Font(R.font.roboto_bold)),
                            fontWeight = FontWeight(600),
                            fontSize = 12.sp
                        )
                        TextField(
                            value = bio.text,
                            onValueChange = { bio = bio.copy(text = it) },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Write a short Bio",
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontFamily = FontFamily(Font(R.font.roboto_light)),
                                    fontWeight = FontWeight(300),
                                    fontSize = 12.sp
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            colors = customColor,
                            trailingIcon = {
                                if (bio.text.isNotEmpty() && bio.text.length >= minLength) {
                                    IconButton(onClick = { bio = TextFieldValue() }) {
                                        Icon(
                                            imageVector = Icons.Outlined.Check,
                                            tint = Color(0xFF006400),
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(104.dp),
                        )
                    }
                }



                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp, bottom = 70.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    colors = CardDefaults.cardColors(Color(0xFF4CAF50))
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
                                        append("First Name: ${firstName.text}\n")
                                        append("Last Name: ${lastName.text}\n")
                                        append("Email Address: ${emailAddress.text}\n")
                                        append("Home Address: ${homeAddress.text}\n")
                                        append("Phone Number: ${phoneNumber.text}\n")
                                        append("Instagram Handle: ${instagramHandle.text}\n")
                                        append("Twitter Handle: ${twitterHandle.text}\n")
                                        append("Bio: ${bio.text}")
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
                                text = "Generate QR Code",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(600),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            }

        }
    }


}

@Composable
fun IdentityCard(firstName: String?, lastName: String?) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 82.dp)
                    .aspectRatio(1.586f), // Standard credit card aspect ratio
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimaryContainer)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        // First Name
                        Text(
                            text = firstName ?: "First Name",
                            modifier = Modifier
                                .align(Alignment.Start),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily(Font(R.font.roboto_black)),
                            fontWeight = FontWeight(600),
                            fontSize = 24.sp
                        )

                        // Last Name
                        Text(
                            text = lastName ?: "Last Name",
                            modifier = Modifier
                                .align(Alignment.Start),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontFamily = FontFamily(Font(R.font.roboto_black)),
                            fontWeight = FontWeight(600),
                            fontSize = 24.sp
                        )

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.End, // Align the image to the end (right side)
                        verticalAlignment = Alignment.Bottom // Align the image to the bottom
                    ) {
                        // Logo (Replace with your logo)
                        Image(
                            painter = painterResource(id = R.drawable.pattern_bg), // Replace with your logo resource
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .alpha(0.5f),
                            alignment = Alignment.BottomEnd
                        )
                    }
                }

        }
    }
}
