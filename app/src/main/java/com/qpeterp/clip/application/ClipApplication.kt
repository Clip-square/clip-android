package com.qpeterp.clip.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClipApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceManager
        private lateinit var instance: ClipApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}