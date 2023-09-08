package com.shegs.identityqr.ui.screens

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shegs.identityqr.R
import com.shegs.identityqr.navigation.topnav.TopNavigation
import com.shegs.identityqr.util.saveQrCodeToGallery
import com.shegs.identityqr.util.shareQRCode

@Composable
fun QRDisplayScreen(qrCodeBitmap: ImageBitmap, navController: NavController) {

    val isHorizontal = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3FA))
    ) {
        TopNavArea(navController, qrCodeBitmap)

        if (!isHorizontal) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)

            ) {

                val context = LocalContext.current

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                        ) {
                            Image(
                                bitmap = qrCodeBitmap,
                                contentDescription = "QR Code",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Fit
                            )
                        }

                    }

                    Text(
                        text = "QR Code Generated \uD83D\uDC4D",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 20.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_black)),
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 52.dp, end = 52.dp, top = 28.dp, bottom = 12.dp)
                    )


                    Text(
                        text = "Kindly click the button below to share your QR card or save to gallery using the save icon above",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(300),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 52.dp, end = 52.dp, bottom = 52.dp)
                    )


                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(bottom = 24.dp, start = 24.dp, end = 24.dp)
                            .clickable {
                                shareQRCode(context, qrCodeBitmap)
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
                                contentDescription = "share"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
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

                }
            }
        }

        else {
            // When in horizontal orientation, display content with scrolling

            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 120.dp, end = 120.dp, bottom = 70.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Left side: Card
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Card(
                        modifier = Modifier
                            .width(180.dp)
                            .height(180.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Image(
                            bitmap = qrCodeBitmap,
                            contentDescription = "QR Code",
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                // Right side: Column with Text and Card button
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "QR Code Generated \uD83D\uDC4D",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 20.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_black)),
                        fontWeight = FontWeight(600),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(top = 28.dp, bottom = 12.dp)
                    )

                    Text(
                        text = "Kindly click the button below to share your QR card or save to gallery using the save icon above",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(300),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clickable {
                                shareQRCode(context, qrCodeBitmap)
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
                                contentDescription = "share"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
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
                }
            }
        }

    }
}


@Composable
fun TopNavArea(navController: NavController, qrCodeBitmap: ImageBitmap) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            TopNavigation(navController, "Generated QR")
        }

        Box(
            modifier = Modifier
                .width(28.dp)
                .height(28.dp)
                .clickable {
                    saveQrCodeToGallery(context, qrCodeBitmap, "MyQRCode")
                    Toast
                        .makeText(context, "Saved Successfully", Toast.LENGTH_SHORT)
                        .show()
                },
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = "Save QR Code",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        Log.d("QRCodeDebug", "qrCodeBitmap: $qrCodeBitmap")
                        saveQrCodeToGallery(context, qrCodeBitmap, "MyQRCode")
                        Toast
                            .makeText(context, "Saved Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
            )
        }
    }
}