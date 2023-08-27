package com.shegs.identityqr.ui.state

import android.graphics.Bitmap
import com.shegs.identityqr.model.Information
import java.util.Date

data class InformationState(
    var information: List<Information> = emptyList(),

    var isAddingInformation: Boolean = false,

    val infoId: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val cardName: String = "",
    val emailAddress: String = "",
    val homeAddress: String = "",
    val phoneNumber: String = "",
    val instagramHandle: String = "",
    val twitterHandle: String = "",
    val bio: String = "",
    val qrcodeImage: Bitmap? = null,
    val createdAt: Date
)
