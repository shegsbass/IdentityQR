package com.shegs.identityqr.ui.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class QRViewModel : ViewModel() {
    private val _qrCodeBitmap = MutableStateFlow<ImageBitmap?>(null)

    val qrCodeBitmap: StateFlow<ImageBitmap?> = _qrCodeBitmap

    fun setQRCodeBitmap(bitmap: ImageBitmap) {
        _qrCodeBitmap.value = bitmap
    }
}