package com.adityakamble49.mcrypt.interactor

import android.net.Uri
import com.adityakamble49.mcrypt.cache.file.FileStorageHelper
import com.adityakamble49.mcrypt.model.FileType
import com.adityakamble49.mcrypt.utils.Constants.MCryptFileFormats
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Get File Extension Use Case
 *
 * @author Aditya Kamble
 * @since 25/1/2018
 */
class GetFileTypeUseCase @Inject constructor(
        private val fileStorageHelper: FileStorageHelper) {

    private fun buildUseCaseObservable(uri: Uri): Single<FileType> {
        return Single.create(object : SingleOnSubscribe<FileType> {
            override fun subscribe(e: SingleEmitter<FileType>) {
                val fileName = getFileName(uri)
                val fileType = getFileType(fileName)
                e.onSuccess(fileType)
            }
        })
    }

    private fun getFileName(uri: Uri): String {
        return fileStorageHelper.getFileName(uri)
    }

    private fun getFileType(fileName: String): FileType {
        val fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1)
        Timber.i(fileExtension)
        return when (fileExtension) {
            MCryptFileFormats.MCRYPT_KEY -> FileType.MKF
            MCryptFileFormats.MCRYPT_ENCRYPTED_FILE -> FileType.MEF
            else -> FileType.UNKNOWN
        }
    }

    fun execute(uri: Uri): Single<FileType> {
        return buildUseCaseObservable(uri)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}