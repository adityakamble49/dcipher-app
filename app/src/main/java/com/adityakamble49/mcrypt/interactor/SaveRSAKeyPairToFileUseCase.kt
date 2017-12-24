package com.adityakamble49.mcrypt.interactor

import android.net.Uri
import com.adityakamble49.mcrypt.cache.file.FileStorageHelper
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save RSAKeyPair to File UseCase
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class SaveRSAKeyPairToFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(rsaKeyPair: RSAKeyPair): Single<Uri> {
        return Single.create(object : SingleOnSubscribe<Uri> {
            override fun subscribe(e: SingleEmitter<Uri>) {
                val fileUri = fileStorageHelper.writeRSAKeyToFile(rsaKeyPair)
                e.onSuccess(fileUri)
            }
        })
    }

    fun execute(rsaKeyPair: RSAKeyPair): Single<Uri> {
        return buildUseCaseObservable(rsaKeyPair)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}