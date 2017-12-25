package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.di.scope.PerActivity
import com.adityakamble49.mcrypt.interactor.EncryptTextUseCase
import com.adityakamble49.mcrypt.ui.encrypt.EncryptViewModelFactory
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
            encryptTextUseCase: EncryptTextUseCase):
            EncryptViewModelFactory = EncryptViewModelFactory(encryptTextUseCase)
}