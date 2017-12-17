package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.di.scope.PerActivity
import com.adityakamble49.mcrypt.ui.MainActivity
import com.adityakamble49.mcrypt.ui.keys.KeyManagerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activity Binding Module used to enable Android Injector in activity
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [(CommonActivityModule::class)])
    abstract fun contributeMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [(KeyManagerActivityModule::class)])
    abstract fun contributeKeyManagerActivity(): KeyManagerActivity
}