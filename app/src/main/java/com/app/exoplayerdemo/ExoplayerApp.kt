package com.app.exoplayerdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ExoplayerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                with(element) {
                    return "($fileName:$lineNumber)$methodName()"
                }
            }
        })
    }
}