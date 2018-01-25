package com.adityakamble49.dcipher.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.adityakamble49.dcipher.cache.db.DCipherDatabase
import com.adityakamble49.dcipher.cache.db.EncryptionKeyDao
import com.adityakamble49.dcipher.di.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Application module for DCipher App
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context = application.applicationContext


    @Provides
    @PerApplication
    fun provideEncryptionKeyDao(
            DCipherDatabase: DCipherDatabase): EncryptionKeyDao = DCipherDatabase.encryptionKeyDao()

    @Provides
    @PerApplication
    fun provideDCipherDatabase(application: Application): DCipherDatabase =
            Room.databaseBuilder(application.applicationContext, DCipherDatabase::class.java,
                    "dcipher.db").build()
}