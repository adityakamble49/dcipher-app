package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.model.EncryptionKey
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
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(uri: Uri): Single<EncryptionKey> {
        return Single.create(object : SingleOnSubscribe<EncryptionKey> {
            override fun subscribe(e: SingleEmitter<EncryptionKey>) {
                val encryptionKey = fetchEncryptionKeyFromFile(uri)
                e.onSuccess(encryptionKey)
            }
        })
    }

    private fun fetchEncryptionKeyFromFile(uri: Uri): EncryptionKey {
        return fileStorageHelper.readObjectFromFile(uri) as EncryptionKey
    }

    fun execute(uri: Uri): Single<EncryptionKey> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}