package com.example.gymsapp

import android.app.Application
import android.content.Context

class GymsApplication: Application() {

    init {
        application = this
    }
    companion object {
        private lateinit var application: Application
        fun getApplicationContext(): Context = application.applicationContext

    }

}