package com.example.gymsapp.gyms.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGym::class],
    version = 3,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {

    abstract fun getDao(): GymsDAO

}

