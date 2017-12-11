package com.adityakamble49.mcrypt.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.adityakamble49.mcrypt.model.RSAKeyPair

/**
 * MCryptDatabase
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Database(entities = [(RSAKeyPair::class)], version = 1)
abstract class MCryptDatabase : RoomDatabase() {

    abstract fun rsaKeyPairDao(): RSAKeyPairDao
}