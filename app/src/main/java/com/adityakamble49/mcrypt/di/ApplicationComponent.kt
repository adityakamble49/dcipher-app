package com.adityakamble49.mcrypt.di

import android.app.Application
import com.adityakamble49.mcrypt.MCryptApp
import com.adityakamble49.mcrypt.di.module.ActivityBindingModule
import com.adityakamble49.mcrypt.di.scope.PerApplication
import com.adityakamble49.mcrypt.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Application Component for MCrypt App
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

    fun inject(app: MCryptApp)
}