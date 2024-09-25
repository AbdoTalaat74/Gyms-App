package com.example.gymsapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Gym::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {

    abstract fun getDao(): GymsDAO

    companion object {
        @Volatile
        private var daoInstance: GymsDAO? = null
        private fun buildDataBase(context: Context): GymsDatabase =
            Room.databaseBuilder(
                context,
                GymsDatabase::class.java,
                "gyms_database"
            )
                .fallbackToDestructiveMigration()
                .build()

         fun getDaoInstance(context: Context): GymsDAO {
            synchronized(this) {
                if (daoInstance == null) {
                    daoInstance = buildDataBase(context).getDao()
                }
                return daoInstance as GymsDAO
            }

        }
    }

}

