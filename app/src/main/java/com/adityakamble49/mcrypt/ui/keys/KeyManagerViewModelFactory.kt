package com.adityakamble49.mcrypt.ui.keys

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.adityakamble49.mcrypt.cache.db.EncryptionKeyRepo
import com.adityakamble49.mcrypt.interactor.*

/**
 * KeyManager ViewModel Factory
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModelFactory(
        private val encryptionKeyRepo: EncryptionKeyRepo,
        private val buildEncryptionKeyUseCase: BuildEncryptionKeyUseCase,
        private val saveEncryptionKeyUseCase: SaveEncryptionKeyUseCase,
        private val saveEncryptionKeyToFileUseCase: SaveEncryptionKeyToFileUseCase,
        private val deleteEncryptionKeyUseCase: DeleteEncryptionKeyUseCase,
        private val getEncryptionKeyFromFileUseCase: GetEncryptionKeyFromFileUseCase) :
        ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyManagerViewModel::class.java)) {
            return KeyManagerViewModel(encryptionKeyRepo, buildEncryptionKeyUseCase,
                    saveEncryptionKeyUseCase, saveEncryptionKeyToFileUseCase,
                    deleteEncryptionKeyUseCase, getEncryptionKeyFromFileUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}