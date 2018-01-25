package com.adityakamble49.mcrypt.interactor

import android.net.Uri
import com.adityakamble49.mcrypt.cache.file.FileStorageHelper
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get Text to From File UseCase
 *
 * @author Aditya Kamble
 * @since 25/1/2018
 */
class GetTextFromFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(uri: Uri): Single<String> {
        return Single.create(object : SingleOnSubscribe<String> {
            override fun subscribe(e: SingleEmitter<String>) {
                val encryptedText = fetchTextFromFile(uri)
                e.onSuccess(encryptedText)
            }
        })
    }

    private fun fetchTextFromFile(uri: Uri): String {
        return fileStorageHelper.readTextFromFile(uri)
    }

    fun execute(uri: Uri): Single<String> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}