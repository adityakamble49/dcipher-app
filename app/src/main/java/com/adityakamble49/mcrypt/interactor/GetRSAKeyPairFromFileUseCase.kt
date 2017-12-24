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
 * Get RSAKeyPair to From UseCase
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class GetRSAKeyPairFromFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(uri: Uri): Single<RSAKeyPair> {
        return Single.create(object : SingleOnSubscribe<RSAKeyPair> {
            override fun subscribe(e: SingleEmitter<RSAKeyPair>) {
                val rsaKeyPair = fileStorageHelper.fetchRSAKeyPairFromFile(uri)
                e.onSuccess(rsaKeyPair)
            }
        })
    }

    fun execute(uri: Uri): Single<RSAKeyPair> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}