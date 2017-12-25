package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.di.scope.PerActivity
import com.adityakamble49.mcrypt.interactor.GetCurrentEncryptionKeyUseCase
import com.adityakamble49.mcrypt.ui.common.CommonViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * Common Activity module to provide General specific dependencies
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
@Module
class CommonActivityModule {

    @Provides
    @PerActivity
    fun provideCommonViewModelFactory(
            getCurrentEncryptionKeyUseCase: GetCurrentEncryptionKeyUseCase):
            CommonViewModelFactory = CommonViewModelFactory(getCurrentEncryptionKeyUseCase)
}