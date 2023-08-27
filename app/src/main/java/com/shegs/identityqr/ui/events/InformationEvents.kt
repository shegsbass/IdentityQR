package com.shegs.identityqr.ui.events

import android.graphics.Bitmap
import com.shegs.identityqr.model.Information
import java.util.Date

sealed interface InformationEvents{

    data class SaveUserInput(val userInput: UserInput): InformationEvents

    data class UserInput(
        val infoId: Int = 0,
        val firstName: String,
        val lastName: String,
        val cardName: String,
        val emailAddress: String,
        val homeAddress: String,
        val phoneNumber: String,
        val instagramHandle: String,
        val twitterHandle: String,
        val bio: String,
        val qrCodeImageBitmap: Bitmap?,
        val createdAt: Date
    )

    data class SetUserInfo(val userInfo: UserInfo): InformationEvents

    data class UserInfo(
        val firstName: String,
        val lastName: String,
        val cardName: String,
        val emailAddress: String,
        val homeAddress: String,
        val phoneNumber: String,
        val instagramHandle: String,
        val twitterHandle: String,
        val bio: String,
        val qrCodeImageBitmap: Bitmap?
    )

    data class DeleteInformation(val information: Information): InformationEvents

    data class SetCardName(val cardName: String): InformationEvents

    data class SetTimeStamp(val createdAt: Date): InformationEvents

    object ShowDialog: InformationEvents

    object HideDialog: InformationEvents
}