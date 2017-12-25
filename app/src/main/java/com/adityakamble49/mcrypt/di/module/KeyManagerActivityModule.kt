package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.cache.db.EncryptionKeyRepo
import com.adityakamble49.mcrypt.di.scope.PerActivity
import com.adityakamble49.mcrypt.interactor.*
import com.adityakamble49.mcrypt.ui.keys.KeyManagerViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Key Manager Activity module to provide KeyManager specific dependencies
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
@Module
class KeyManagerActivityModule {

    @Provides
    @PerActivity
    fun provideKeyManagerViewModelFactory(encryptionKeyRepo: EncryptionKeyRepo,
                                          buildEncryptionKeyUseCase: BuildEncryptionKeyUseCase,
                                          saveEncryptionKeyUseCase: SaveEncryptionKeyUseCase,
                                          saveEncryptionKeyToFileUseCase: SaveEncryptionKeyToFileUseCase,
                                          deleteEncryptionKeyUseCase: DeleteEncryptionKeyUseCase,
                                          getEncryptionKeyFromFileUseCase: GetEncryptionKeyFromFileUseCase):
            KeyManagerViewModelFactory = KeyManagerViewModelFactory(
            encryptionKeyRepo, buildEncryptionKeyUseCase, saveEncryptionKeyUseCase,
            saveEncryptionKeyToFileUseCase, deleteEncryptionKeyUseCase,
            getEncryptionKeyFromFileUseCase)
}