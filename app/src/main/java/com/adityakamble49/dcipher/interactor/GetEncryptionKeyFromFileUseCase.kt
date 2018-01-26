package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.data.mapper.KeySpecMapper
import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.model.EncryptionKeyShare
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get EncryptionKey to From UseCase
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class GetEncryptionKeyFromFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper,
        private val keySpecMapper: KeySpecMapper) {

    private fun buildUseCaseObservable(uri: Uri): Single<EncryptionKey> {
        return Single.create(object : SingleOnSubscribe<EncryptionKey> {
            override fun subscribe(e: SingleEmitter<EncryptionKey>) {
                val encryptionKey = fetchEncryptionKeyFromFile(uri)
                e.onSuccess(encryptionKey)
            }
        })
    }

    private fun fetchEncryptionKeyFromFile(uri: Uri): EncryptionKey {
        val encryptionKeyStr = fileStorageHelper.readObjectFromFile(uri) as String
        val encryptionKeyShare = Gson().fromJson(encryptionKeyStr, EncryptionKeyShare::class.java)
        return keySpecMapper.encryptionShareToKey(encryptionKeyShare)
    }

    fun execute(uri: Uri): Single<EncryptionKey> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}