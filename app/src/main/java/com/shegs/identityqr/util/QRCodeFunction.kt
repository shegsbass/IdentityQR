package com.shegs.identityqr.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun generateQRCode(qrText: String, size: Int = 300): Bitmap? {
    Log.d("QRCode", "Generating QR Code for text: $qrText")
    val barcodeEncoder = BarcodeEncoder()
    val bitmap = barcodeEncoder.encodeBitmap(qrText, BarcodeFormat.QR_CODE, size, size)
    return bitmap
}

//fun generateQRCode(text: String, size: Int = 300): Bitmap? {
//    try {
//        val writer = QRCodeWriter()
//        val bitMatrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size)
//        val width = bitMatrix.width
//        val height = bitMatrix.height
//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//            }
//        }
//
//        return bitmap
//    } catch (e: Exception) {
//        e.printStackTrace()
//        return null
//    }
//}

fun shareQRCode(context: Context, qrCodeBitmap: ImageBitmap) {

    val androidBitmap = qrCodeBitmap.asAndroidBitmap()

    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"

        val qrImagePath = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            androidBitmap,
            "QR Code",
            "QR Code generated by IdMe"
        )

        val qrImageUri = Uri.parse(qrImagePath)
        putExtra(Intent.EXTRA_STREAM, qrImageUri)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share QR Code"))
}


fun saveQrCodeToGallery(context: Context, qrCodeBitmap: ImageBitmap, fileName: String): Boolean {
    val androidBitmap = qrCodeBitmap.asAndroidBitmap()
    val imageFileName = "$fileName.png"

    // Check if external storage is available
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imageDir, imageFileName)

        try {
            val outputStream: OutputStream = FileOutputStream(imageFile)
            androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Add the image to the device's gallery
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                Toast.makeText(context, "Saved to gallery", Toast.LENGTH_SHORT).show()
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    return false
}