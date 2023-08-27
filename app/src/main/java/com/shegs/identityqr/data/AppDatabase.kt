package com.shegs.identityqr.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shegs.identityqr.model.Information
import com.shegs.identityqr.util.DateConverter
import com.shegs.identityqr.util.LocalDateTimeConverter

@Database(entities = [Information::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, LocalDateTimeConverter::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun informationDao() : InformationDAO
}