package com.adityakamble49.mcrypt.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.adityakamble49.mcrypt.model.RSAKeyPair

/**
 * RSA Key Pair - Data Access Object
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Dao
interface RSAKeyPairDao {

    @Insert(onConflict = REPLACE)
    fun insertRSAKeyPair(rsaKeyPair: RSAKeyPair)

    @Query("SELECT * FROM rsa_key_pair")
    fun getRSAKeyPairList(): LiveData<List<RSAKeyPair>>
}