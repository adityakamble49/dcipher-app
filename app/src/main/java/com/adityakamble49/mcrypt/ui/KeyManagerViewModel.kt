package com.adityakamble49.mcrypt.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.adityakamble49.mcrypt.db.RSAKeyPairRepo
import com.adityakamble49.mcrypt.model.RSAKeyPair
import javax.inject.Inject

/**
 * View Model for Key Manager Activity
 *
 * @author Aditya Kamble
 * @since 13/12/2017
 */
class KeyManagerViewModel @Inject constructor(
        private val rsaKeyPairRepo: RSAKeyPairRepo) : ViewModel() {

    val rsaKeyPairList: LiveData<List<RSAKeyPair>> = rsaKeyPairRepo.getRSAKeyPairList()
}