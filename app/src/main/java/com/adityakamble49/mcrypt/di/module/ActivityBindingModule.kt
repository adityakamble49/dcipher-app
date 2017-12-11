package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.MainActivity
import com.adityakamble49.mcrypt.di.scope.PerActivity
import com.adityakamble49.mcrypt.ui.KeyManagerActivity
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
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeKeyManagerActivity(): KeyManagerActivity
}