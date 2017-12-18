package com.adityakamble49.mcrypt.ui.common

import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.interactor.GetCurrentRSAKeyPairUseCase
import com.adityakamble49.mcrypt.model.RSAKeyPair
import io.reactivex.Observer
import javax.inject.Inject

/**
 * Common View Model for MCrypt
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class CommonViewModel @Inject constructor(
        private val getCurrentRSAKeyPairUseCase: GetCurrentRSAKeyPairUseCase) : ViewModel() {

    fun requestCurrentRSAKeyPair(observer: Observer<RSAKeyPair>) {
        getCurrentRSAKeyPairUseCase.execute().subscribe(observer)
    }
}