package com.example.persecdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PersecApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}