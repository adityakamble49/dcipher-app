package com.adityakamble49.mcrypt

import android.app.Activity
import android.app.Application
import com.adityakamble49.mcrypt.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Aditya Kamble
 * @since 8/12/2017
 */
class MCryptApp : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupDagger()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDagger() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}