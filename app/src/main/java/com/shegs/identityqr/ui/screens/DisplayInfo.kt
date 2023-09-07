package com.shegs.identityqr.ui.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shegs.identityqr.R
import com.shegs.identityqr.ui.viewmodel.InformationViewModel
import com.shegs.identityqr.util.saveQrCodeToGallery
import com.shegs.identityqr.util.shareQRCode

@SuppressLint("SuspiciousIndentation")
@Composable
fun DisplayInfoScreen(infoId: Int, viewModel: InformationViewModel) {

    // Check if infoId is not null before using it
    if (infoId != null) {
        viewModel.getSelectedInformation(infoId)

    } else {
        Text(text = "Information not found.")
    }

    val card by viewModel.selectedInformation.collectAsState()

    val context = LocalContext.current

    var qrCodeBitmap: ImageBitmap? = null

//    TopNavigation(navController, "${card?.cardName}" + " QR Details" )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3FA))
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 40.dp)
    ) {
        item {

            DragBar()

            card?.let { card ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 60.dp, end = 60.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {

                        // Display the QR code image if it's available in the card
                        card.qrCodeImage?.let { qrCodeImageByteArray ->
                            val imageBitmap = BitmapFactory.decodeByteArray(
                                qrCodeImageByteArray,
                                0,
                                qrCodeImageByteArray.size
                            )?.asImageBitmap()
                            if (imageBitmap != null) {
                                qrCodeBitmap = imageBitmap // Update qrCodeBitmap here
                                Image(
                                    bitmap = qrCodeBitmap!!,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(8.dp)
                                )

                            }
                        }

                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "First Name:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.firstName}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Last Name:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.lastName}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Email Address:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.emailAddress}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Home Address:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.homeAddress}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Phone Number:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.phoneNumber}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Instagram Handle:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.instagramHandle}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Twitter Handle:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.twitterHandle}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Bio:",
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(300),
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "${card.bio}",
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        fontWeight = FontWeight(600),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                qrCodeBitmap?.let { DisplayInfoButtons(it) }
            }

            }
        }
}

@Composable
fun DragBar() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(4.dp)
                .background(MaterialTheme.colorScheme.onPrimaryContainer),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}

@Composable
fun DisplayInfoButtons(qrCodeBitmap: ImageBitmap) {

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(70.dp)
            .padding(bottom = 24.dp, start = 18.dp, )
            .clickable {
                shareQRCode(context, qrCodeBitmap!!)
            },
        colors = CardDefaults.cardColors(Color(0xFF4CAF50)),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "share",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Share",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                fontWeight = FontWeight(600),
                fontSize = 16.sp
            )
        }
    }

    Card(
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .padding(bottom = 24.dp, start = 4.dp, end = 18.dp)
            .clickable {
                saveQrCodeToGallery(context, qrCodeBitmap, "MyQRCode")
                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onPrimaryContainer),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = "Save",
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}




