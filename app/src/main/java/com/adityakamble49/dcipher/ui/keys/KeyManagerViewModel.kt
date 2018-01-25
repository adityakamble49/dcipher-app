package com.adityakamble49.dcipher.ui.keys

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.adityakamble49.dcipher.cache.db.EncryptionKeyRepo
import com.adityakamble49.dcipher.interactor.*
import com.adityakamble49.dcipher.model.EncryptionKey
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

/**
 * View Model for Key Manager Activity
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModel @Inject constructor(
        private val encryptionKeyRepo: EncryptionKeyRepo,
        private val buildEncryptionKeyUseCase: BuildEncryptionKeyUseCase,
        private val saveEncryptionKeyUseCase: SaveEncryptionKeyUseCase,
        private val saveEncryptionKeyToFileUseCase: SaveEncryptionKeyToFileUseCase,
        private val deleteEncryptionKeyUseCase: DeleteEncryptionKeyUseCase,
        private val getEncryptionKeyFromFileUseCase: GetEncryptionKeyFromFileUseCase) : ViewModel() {

    val encryptionKeyList: LiveData<List<EncryptionKey>> = encryptionKeyRepo.getEncryptionKeyList()
    lateinit var saveRSAKeyObserver: CompletableObserver

    fun generateAndSaveEncryptionKey(keyName: String, observer: CompletableObserver) {
        saveRSAKeyObserver = observer
        buildEncryptionKeyUseCase.execute(keyName).subscribe(BuildEncryptionKeySubscriber())
    }

    fun saveEncryptionKeyToDb(encryptionKey: EncryptionKey, observer: CompletableObserver) {
        saveEncryptionKeyUseCase.execute(encryptionKey).subscribe(observer)
    }

    fun saveEncryptionKeyToFile(encryptionKey: EncryptionKey, observer: SingleObserver<Uri>) {
        saveEncryptionKeyToFileUseCase.execute(encryptionKey).subscribe(observer)
    }

    fun deleteEncryptionKey(encryptionKey: EncryptionKey, observer: CompletableObserver) {
        deleteEncryptionKeyUseCase.execute(encryptionKey).subscribe(observer)
    }

    fun getEncryptionKeyFromFile(uri: Uri, observer: SingleObserver<EncryptionKey>) {
        getEncryptionKeyFromFileUseCase.execute(uri).subscribe(observer)
    }

    inner class BuildEncryptionKeySubscriber : Observer<EncryptionKey> {
        override fun onSubscribe(d: Disposable) {}

        override fun onNext(t: EncryptionKey) {
            saveEncryptionKeyUseCase.execute(t).subscribe(saveRSAKeyObserver)
        }

        override fun onComplete() {}

        override fun onError(e: Throwable) {
            Timber.i(e)
        }
    }
}