package com.adityakamble49.mcrypt.interactor

import android.net.Uri
import com.adityakamble49.mcrypt.cache.file.FileStorageHelper
import com.adityakamble49.mcrypt.model.EncryptionKey
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save [[EncryptionKey]] to File UseCase
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class SaveEncryptionKeyToFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(encryptionKey: EncryptionKey): Single<Uri> {
        return Single.create(object : SingleOnSubscribe<Uri> {
            override fun subscribe(e: SingleEmitter<Uri>) {
                val fileUri = fileStorageHelper.writeEncryptionKeyToFile(encryptionKey)
                e.onSuccess(fileUri)
            }
        })
    }

    fun execute(encryptionKey: EncryptionKey): Single<Uri> {
        return buildUseCaseObservable(encryptionKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}