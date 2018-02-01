package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.model.EncryptedText
import com.adityakamble49.dcipher.utils.Constants.DCipherDir
import com.adityakamble49.dcipher.utils.Constants.DCipherFileFormats
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save Encrypted Text to File UseCase
 *
 * @author Aditya Kamble
 * @since 31/12/2017
 */
class SaveEncryptedTextToFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(encryptedFileName: String,
                                       encryptedText: String): Single<Uri> {
        return Single.create(object : SingleOnSubscribe<Uri> {
            override fun subscribe(e: SingleEmitter<Uri>) {
                val fileUri = writeEncryptedTextToFile(encryptedFileName, encryptedText)
                e.onSuccess(fileUri)
            }
        })
    }

    private fun writeEncryptedTextToFile(encryptedFileName: String, encryptedText: String): Uri {
        val fileName = "$encryptedFileName.${DCipherFileFormats.DCIPHER_ENCRYPTED_FILE}"
        val encryptedTextObj = EncryptedText(encryptedText)
        val encryptedTextStr = Gson().toJson(encryptedTextObj)
        return fileStorageHelper.writeObjectToFile(DCipherDir.DCIPHER_ENCRYPTED_FILES, fileName,
                encryptedTextStr)
    }

    fun execute(encryptedFileName: String, encryptedText: String): Single<Uri> {
        return buildUseCaseObservable(encryptedFileName, encryptedText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}