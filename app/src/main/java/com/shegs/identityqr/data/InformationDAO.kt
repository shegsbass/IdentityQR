package com.shegs.identityqr.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shegs.identityqr.model.Information
import kotlinx.coroutines.flow.Flow

@Dao
interface InformationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInformation(information: Information)

    @Delete
    suspend fun deleteInformation(information: Information)

    @Query("SELECT * FROM INFORMATION_TABLE WHERE info_id=:infoId")
    fun getSelectedInformation(infoId: Int): Flow<Information>

    @Query("SELECT * FROM INFORMATION_TABLE")
    suspend fun getAllInformation(): List<Information>
}