package com.shegs.identityqr.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QRDisplayScreen(qrCodeBitmap: ImageBitmap, navController: NavController ) {

    var thumbsUpVisible by remember { mutableStateOf(false) }
    val thumbsUpPositions = remember { mutableStateListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f) }
    val coroutineScope = rememberCoroutineScope()

    fun animateThumbsUp() {
        coroutineScope.launch {
            thumbsUpVisible = true

            // Animate the positions of each emoji
            for (i in 0 until 10) {
                thumbsUpPositions[i] = -300f // Start below the screen
            }

            delay(1000) // Delay before animating

            for (i in 0 until 10) {
                thumbsUpPositions[i] = 0f // Animate to the top
            }

            delay(1000) // Delay at the top

            thumbsUpVisible = false // Hide the emojis after the animation
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F3FA))

    ) {
        val context = LocalContext.current

        TopNavigation(navController, "Generated QR")

        // Start the animation when the screen opens
        LaunchedEffect(Unit) {
            animateThumbsUp()
        }

        // Display the animated emojis
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
                if (thumbsUpVisible) {
                    thumbsUpPositions.forEachIndexed { index, positionY ->
                    AnimatedThumbsUp(
                        thumbsUpPositionY = positionY,
                        delayMillis = (index * 100).toLong() // Delay each emoji
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, end = 16.dp)
                .aspectRatio(1f),
            contentAlignment = Alignment.TopEnd
        ) {

            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = "Save QR Code",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { saveQrCodeToGallery(context, qrCodeBitmap, "MyQRCode") }
            )
        }

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
                        .fillMaxSize(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                ) {
                    Image(
                        bitmap = qrCodeBitmap,
                        contentDescription = "QR Code",
                        modifier = Modifier
                            .fillMaxSize(),
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

@Composable
fun AnimatedThumbsUp(thumbsUpPositionY: Float, delayMillis: Long) {
    val transition = rememberInfiniteTransition()
    val translateY by transition.animateFloat(
        initialValue = thumbsUpPositionY,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, delayMillis = delayMillis.toInt()),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .offset(y = translateY.dp)
            .clickable { /* Handle click here */ }
    ) {
        Text(
            text = "\uD83D\uDC4D", // Thumbs up emoji
            fontSize = 40.sp,
            color = Color.Green
        )
    }
}