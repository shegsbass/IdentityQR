package com.shegs.identityqr.ui.viewmodel

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shegs.identityqr.model.Information
import com.shegs.identityqr.repositories.InformationRepository
import com.shegs.identityqr.ui.events.InformationEvents
import com.shegs.identityqr.ui.state.InformationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class InformationViewModel @Inject constructor(
    private val informationRepository: InformationRepository
): ViewModel(){

    var firstName = mutableStateOf(TextFieldValue())
    var lastName = mutableStateOf(TextFieldValue())
    var cardName = mutableStateOf("")
    var emailAddress = mutableStateOf(TextFieldValue())
    var homeAddress = mutableStateOf(TextFieldValue())
    var phoneNumber = mutableStateOf(TextFieldValue())
    var instagramHandle = mutableStateOf(TextFieldValue())
    var twitterHandle = mutableStateOf(TextFieldValue())
    var bio = mutableStateOf(TextFieldValue())



    private val currentDate = Date()
    private val _infoState = MutableStateFlow(InformationState(createdAt = currentDate))
    val infoState =_infoState

    private val _selectedInformation: MutableStateFlow<Information?> = MutableStateFlow(null)
    val selectedInformation: MutableStateFlow<Information?> = _selectedInformation

    private val _getAllInformation = MutableStateFlow<List<Information>>(emptyList())
    val getAllInformation: StateFlow<List<Information>> = _getAllInformation

    init {
        fetchAllInfo()
    }

    private fun fetchAllInfo(){
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val cards = informationRepository.getAllInformation()
                _getAllInformation.emit(cards)
            }
        }catch (e: Exception){
            Log.d("Information", e.toString())
            _getAllInformation.value
        }
    }

    fun getSelectedInformation(infoId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.getSelectedInformation(infoId = infoId).collect { card ->
                _selectedInformation.value = card
            }
        }
    }

    private fun validateCardNameField(): Boolean {
        return _infoState.value.cardName.isNotEmpty()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event: InformationEvents){
        when(event){
            is InformationEvents.DeleteInformation -> {
                viewModelScope.launch(Dispatchers.IO) {
                    informationRepository.deleteInformation(event.information)
                    fetchAllInfo()
                }
            }

            is InformationEvents.SaveUserInput -> {
                val userInput = event.userInput

                viewModelScope.launch(Dispatchers.IO) {
                    validateCardNameField()
                    val information = Information(
                        firstName = userInput.firstName,
                        lastName = userInput.lastName,
                        cardName = userInput.cardName,
                        emailAddress = userInput.emailAddress,
                        homeAddress = userInput.homeAddress,
                        phoneNumber = userInput.phoneNumber,
                        instagramHandle = userInput.instagramHandle,
                        twitterHandle = userInput.twitterHandle,
                        bio = userInput.bio,
                        qrCodeImage = bitmapToByteArray(userInput.qrCodeImageBitmap),
                        createdAt = LocalDateTime.now()
                    )
                    informationRepository.insertInformation(information)
                    fetchAllInfo()
                    _infoState.update { it.copy(isAddingInformation = false) }
                }
            }

            is InformationEvents.SetCardName -> {
                _infoState.update { it.copy(cardName = event.cardName) }
            }

            is InformationEvents.SetTimeStamp -> {
                _infoState.update { it.copy(createdAt = event.createdAt) }
            }

            is InformationEvents.SetUserInfo -> {
                val userInfo = event.userInfo
                _infoState.update { it.copy(
                    firstName = userInfo.firstName,
                    lastName = userInfo.lastName,
                    cardName = userInfo.cardName,
                    emailAddress = userInfo.emailAddress,
                    homeAddress = userInfo.homeAddress,
                    phoneNumber = userInfo.phoneNumber,
                    instagramHandle = userInfo.instagramHandle,
                    twitterHandle = userInfo.twitterHandle,
                    bio = userInfo.bio,
                    qrcodeImage = userInfo.qrCodeImageBitmap

                ) }
            }

            is InformationEvents.ShowDialog -> {
                _infoState.update { it.copy(isAddingInformation = true) }
            }

            is InformationEvents.HideDialog -> {
                _infoState.update { it.copy(isAddingInformation = false) }
            }
        }
    }

    // Function to convert Bitmap to ByteArray
    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

}