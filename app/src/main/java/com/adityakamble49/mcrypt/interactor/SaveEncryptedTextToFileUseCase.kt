package com.adityakamble49.mcrypt.interactor

import android.net.Uri
import com.adityakamble49.mcrypt.cache.file.FileStorageHelper
import com.adityakamble49.mcrypt.utils.Constants.MCryptDir
import com.adityakamble49.mcrypt.utils.Constants.MCryptFileFormats
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
        val fileName = "$encryptedFileName.${MCryptFileFormats.MCRYPT_ENCRYPTED_FILE}"
        return fileStorageHelper.writeObjectToFile(MCryptDir.MCRYPT_ENCRYPTED_FILES, fileName,
                encryptedText)
    }

    fun execute(encryptedFileName: String, encryptedText: String): Single<Uri> {
        return buildUseCaseObservable(encryptedFileName, encryptedText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}