package com.adityakamble49.dcipher.cache.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.adityakamble49.dcipher.model.EncryptionKey

/**
 * Encryption Key - Data Access Object
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Dao
interface EncryptionKeyDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertEncryptionKey(encryptionKey: EncryptionKey)

    @Query("SELECT * FROM encryption_keys")
    fun getEncryptionKeyList(): LiveData<List<EncryptionKey>>

    @Query("SELECT * FROM encryption_keys WHERE id= :id")
    fun getEncryptionKeyById(id: Int): EncryptionKey?

    @Delete
    fun deleteEncryptionKey(encryptionKey: EncryptionKey)
}