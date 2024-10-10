package com.example.gymsapp.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymsapp.gyms.data.local.GymsDAO
import com.example.gymsapp.gyms.data.local.GymsDatabase
import com.example.gymsapp.gyms.data.remote.GymApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): GymApiService {
        return retrofit.create(GymApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://cairo-gyms-b91c2-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun provideRoomDao(
        db: GymsDatabase
    ): GymsDAO {
        return db.getDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): GymsDatabase {
        return Room.databaseBuilder(
            context,
            GymsDatabase::class.java,
            "gyms_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}