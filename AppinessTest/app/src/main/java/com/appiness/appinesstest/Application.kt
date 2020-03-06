package com.appiness.appinesstest

import android.app.Application
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class Application:MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}