package com.adityakamble49.mcrypt.di.module

import com.adityakamble49.mcrypt.cache.PreferenceHelper
import com.adityakamble49.mcrypt.cache.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.di.scope.PerActivity
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
    fun provideCommonViewModelFactory(rsaKeyPairRepo: RSAKeyPairRepo,
                                      preferenceHelper: PreferenceHelper):
            CommonViewModelFactory = CommonViewModelFactory(rsaKeyPairRepo, preferenceHelper)
}