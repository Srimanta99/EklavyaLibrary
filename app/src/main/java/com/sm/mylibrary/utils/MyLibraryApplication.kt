package com.sm.mylibrary.utils

import android.app.Application

class MyLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
    }
}