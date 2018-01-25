package com.adityakamble49.dcipher.di

import android.app.Application
import com.adityakamble49.dcipher.DCipherApp
import com.adityakamble49.dcipher.di.module.ActivityBindingModule
import com.adityakamble49.dcipher.di.module.ApplicationModule
import com.adityakamble49.dcipher.di.scope.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Application Component for DCipher App
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@PerApplication
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (ActivityBindingModule::class),
    (ApplicationModule::class)])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: DCipherApp)
}