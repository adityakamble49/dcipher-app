package com.adityakamble49.mcrypt

import android.app.Application
import timber.log.Timber

/**
 * @author Aditya Kamble
 * @since 8/12/2017
 */
class MCryptApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}