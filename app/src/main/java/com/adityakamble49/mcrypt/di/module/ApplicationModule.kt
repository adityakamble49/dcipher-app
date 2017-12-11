package com.adityakamble49.mcrypt.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.adityakamble49.mcrypt.db.MCryptDatabase
import com.adityakamble49.mcrypt.db.RSAKeyPairDao
import com.adityakamble49.mcrypt.di.scope.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Application module for MCrypt App
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
    fun provideRSAKeyPairDao(
            mCryptDatabase: MCryptDatabase): RSAKeyPairDao = mCryptDatabase.rsaKeyPairDao()

    @Provides
    @PerApplication
    fun provideMCryptDatabase(application: Application): MCryptDatabase =
            Room.databaseBuilder(application.applicationContext, MCryptDatabase::class.java,
                    "mcrypt.db").build()
}