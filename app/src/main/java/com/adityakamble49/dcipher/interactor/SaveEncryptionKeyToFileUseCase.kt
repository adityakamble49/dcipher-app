package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.data.mapper.KeySpecMapper
import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.utils.Constants.DCipherDir
import com.adityakamble49.dcipher.utils.Constants.DCipherFileFormats
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Save [[EncryptionKey]] to File UseCase
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class SaveEncryptionKeyToFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper,
        private val keySpecMapper: KeySpecMapper) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey): Single<Uri> {
        return Single.create(object : SingleOnSubscribe<Uri> {
            override fun subscribe(e: SingleEmitter<Uri>) {
                val fileUri = writeEncryptionKeyToFile(encryptionKey)
                e.onSuccess(fileUri)
            }
        })
    }

    private fun writeEncryptionKeyToFile(encryptionKey: EncryptionKey): Uri {
        val fileName = "${encryptionKey.name}.${DCipherFileFormats.DCIPHER_KEY}"
        val encryptionKeyStr = Gson().toJson(keySpecMapper.encryptionKeyToShare(encryptionKey))
        Timber.i(encryptionKey.toString())
        Timber.i(encryptionKeyStr)
        return fileStorageHelper.writeObjectToFile(DCipherDir.DCIPHER_KEYS, fileName,
                encryptionKeyStr)
    }

    fun execute(encryptionKey: EncryptionKey): Single<Uri> {
        return buildUseCaseObservable(encryptionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}