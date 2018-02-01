package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.model.EncryptedText
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get Encrypted Text to From File UseCase
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class GetEncryptedTextFromFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(uri: Uri): Single<String> {
        return Single.create(object : SingleOnSubscribe<String> {
            override fun subscribe(e: SingleEmitter<String>) {
                val encryptedText = fetchEncryptedTextFromFile(uri)
                e.onSuccess(encryptedText)
            }
        })
    }

    private fun fetchEncryptedTextFromFile(uri: Uri): String {
        val encryptedTextStr = fileStorageHelper.readObjectFromFile(uri) as String
        val encryptedText = Gson().fromJson(encryptedTextStr, EncryptedText::class.java)
        return encryptedText.encryptedTextValue
    }

    fun execute(uri: Uri): Single<String> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}