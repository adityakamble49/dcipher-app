package com.adityakamble49.mcrypt.cache.db

import android.arch.lifecycle.LiveData
import com.adityakamble49.mcrypt.di.scope.PerApplication
import com.adityakamble49.mcrypt.model.EncryptionKey
import javax.inject.Inject

/**
 * EncryptionKey Repository
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@PerApplication
class EncryptionKeyRepo @Inject constructor(
        private var encryptionKeyDao: EncryptionKeyDao) {

    fun insertEncryptionKey(encryptionKey: EncryptionKey) {
        encryptionKeyDao.insertEncryptionKey(encryptionKey)
    }

    fun getEncryptionKeyList(): LiveData<List<EncryptionKey>> = encryptionKeyDao.getEncryptionKeyList()

    fun getEncryptionKeyById(id: Int): EncryptionKey? = encryptionKeyDao.getEncryptionKeyById(id)

    fun deleteEncryptionKey(encryptionKey: EncryptionKey) {
        encryptionKeyDao.deleteEncryptionKey(encryptionKey)
    }
}