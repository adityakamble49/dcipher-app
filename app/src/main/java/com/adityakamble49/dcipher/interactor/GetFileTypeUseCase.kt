package com.adityakamble49.dcipher.interactor

import android.net.Uri
import com.adityakamble49.dcipher.cache.file.FileStorageHelper
import com.adityakamble49.dcipher.model.FileType
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Get File Extension Use Case
 *
 * @author Aditya Kamble
 * @since 25/1/2018
 */
class GetFileTypeUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private val ENCRYPTED_KEY_FILTER = "encryptedSessionKey"

    private fun buildUseCaseObservable(uri: Uri): Single<FileType> {
        return Single.create(object : SingleOnSubscribe<FileType> {
            override fun subscribe(e: SingleEmitter<FileType>) {
                val fileContent = getFileContent(uri)
                val fileType = getFileType(fileContent)
                e.onSuccess(fileType)
            }
        })
    }

    private fun getFileContent(uri: Uri): Any {
        return fileStorageHelper.readObjectFromFile(uri)
    }

    private fun getFileType(anyObject: Any): FileType {
        val stringObject = anyObject as String
        return if (stringObject.contains(ENCRYPTED_KEY_FILTER)) {
            FileType.EncryptionKey
        } else {
            FileType.EncryptedText
        }
    }

    fun execute(uri: Uri): Single<FileType> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}