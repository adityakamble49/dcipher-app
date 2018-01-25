package com.adityakamble49.dcipher.interactor

import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.utils.Constants.DCipherFileFormats
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Save Decrypted Text to File UseCase
 *
 * @author Aditya Kamble
 * @since 1/11/2018
 */
class SaveDecryptedTextToFileUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(fileDir: String, decryptedFileName: String,
                                       decryptedText: String): Completable {
        return Completable.create(object : CompletableOnSubscribe {
            override fun subscribe(e: CompletableEmitter) {
                fileStorageHelper.writeFileToExternalStorage(fileDir,
                        "$decryptedFileName.${DCipherFileFormats.DCIPHER_DECRYPTED_FILE}",
                        decryptedText)
                e.onComplete()
            }
        })
    }

    fun execute(fileDir: String, decryptedFileName: String, decryptedText: String): Completable {
        return buildUseCaseObservable(fileDir, decryptedFileName, decryptedText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}