package com.adityakamble49.mcrypt.cache.db

import android.arch.lifecycle.LiveData
import com.adityakamble49.mcrypt.di.scope.PerApplication
import com.adityakamble49.mcrypt.model.RSAKeyPair
import javax.inject.Inject

/**
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@PerApplication
class RSAKeyPairRepo @Inject constructor(
        var rsaKeyPairDao: RSAKeyPairDao) {

    fun insertRSAKeyPair(rsaKeyPair: RSAKeyPair) {
        rsaKeyPairDao.insertRSAKeyPair(rsaKeyPair)
    }

    fun getRSAKeyPairList(): LiveData<List<RSAKeyPair>> = rsaKeyPairDao.getRSAKeyPairList()

    fun getRSAKeyPairById(id: Int): RSAKeyPair = rsaKeyPairDao.getRSAKeyPairById(id)
}