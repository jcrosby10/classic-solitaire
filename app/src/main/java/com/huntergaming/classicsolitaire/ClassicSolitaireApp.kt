package com.huntergaming.classicsolitaire

import android.app.Application
import com.huntergaming.gamedata.repository.initFirebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClassicSolitaireApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initFirebase(applicationContext)
    }
}