package com.comst.kakaosearchapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KaKaoSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}