package com.adityakamble49.dcipher.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.adityakamble49.dcipher.model.EncryptionKey

/**
 * DCipherDatabase
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Database(entities = [(EncryptionKey::class)], version = 1)
@TypeConverters(DCipherTypeConverters::class)
abstract class DCipherDatabase : RoomDatabase() {

    abstract fun encryptionKeyDao(): EncryptionKeyDao
}