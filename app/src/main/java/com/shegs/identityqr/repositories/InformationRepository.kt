package com.shegs.identityqr.repositories

import com.shegs.identityqr.data.InformationDAO
import com.shegs.identityqr.model.Information
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class InformationRepository @Inject constructor (private val informationDAO: InformationDAO){

    suspend fun insertInformation(information: Information){
        informationDAO.insertInformation(information)
    }

    suspend fun getAllInformation(): List<Information>{
        return informationDAO.getAllInformation()
    }

    fun getSelectedInformation(infoId: Int): Flow<Information> {
        return informationDAO.getSelectedInformation(infoId = infoId)
    }

    suspend fun deleteInformation(information: Information) {
        informationDAO.deleteInformation(information = information);
    }
}