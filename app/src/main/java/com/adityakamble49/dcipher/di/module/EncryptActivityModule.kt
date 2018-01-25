package com.adityakamble49.dcipher.di.module

import com.adityakamble49.dcipher.di.scope.PerActivity
import com.adityakamble49.dcipher.interactor.EncryptTextUseCase
import com.adityakamble49.dcipher.interactor.GetTextFromFileUseCase
import com.adityakamble49.dcipher.interactor.SaveEncryptedTextToFileUseCase
import com.adityakamble49.dcipher.ui.encrypt.EncryptViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Encrypt Activity module to provide dependencies
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
@Module
class EncryptActivityModule {

    @Provides
    @PerActivity
    fun provideEncryptViewModelFactory(
            encryptTextUseCase: EncryptTextUseCase,
            saveEncryptedTextToFileUseCase: SaveEncryptedTextToFileUseCase,
            getTextFromFileUseCase: GetTextFromFileUseCase):
            EncryptViewModelFactory = EncryptViewModelFactory(encryptTextUseCase,
            saveEncryptedTextToFileUseCase, getTextFromFileUseCase)
}