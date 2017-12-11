package com.adityakamble49.mcrypt.db

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

    fun getRSAKeyPairList(): List<RSAKeyPair> = rsaKeyPairDao.getRSAKeyPairList()
}