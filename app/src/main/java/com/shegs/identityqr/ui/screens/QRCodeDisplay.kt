package com.shegs.identityqr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shegs.identityqr.navigation.topnav.TopNavigation
import com.shegs.identityqr.util.saveQrCodeToGallery
import com.shegs.identityqr.util.shareQRCode

@Composable
fun QRDisplayScreen(qrCodeBitmap: ImageBitmap, navController: NavController ) {

    TopNavigation(navController, "Generated QR" )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center

    ){
        Column (
            modifier = Modifier

        ) {
            val context = LocalContext.current

            Image(
                bitmap = qrCodeBitmap,
                contentDescription = "QR Code",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(
                    onClick = { saveQrCodeToGallery(context, qrCodeBitmap, "MyQRCode") }
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { shareQRCode(context, qrCodeBitmap) }
                ) {
                    Text("Share")
                }
            }

        }
    }
}
