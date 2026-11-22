package com.zyvo.universalprint.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UniversalPrintApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
